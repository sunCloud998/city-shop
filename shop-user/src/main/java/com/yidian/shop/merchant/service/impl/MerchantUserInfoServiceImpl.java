package com.yidian.shop.merchant.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.common.service.UserCacheService;
import com.yidian.shop.component.CurrentRequestComponent;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.merchant.entity.MerchantUserInfo;
import com.yidian.shop.merchant.enums.MerchantStatusEnum;
import com.yidian.shop.merchant.mapper.MerchantUserInfoMapper;
import com.yidian.shop.merchant.param.MerchantListParam;
import com.yidian.shop.merchant.param.MerchantLoginParam;
import com.yidian.shop.merchant.param.MerchantRegisterParam;
import com.yidian.shop.merchant.param.MerchantStatusChangeParam;
import com.yidian.shop.merchant.service.MerchantUserInfoService;
import com.yidian.shop.merchant.vo.MerchantUserInfoVo;
import com.yidian.shop.utils.AvatarUtil;
import com.yidian.shop.vo.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 商家用户表 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-08-04
 */
@Service
public class MerchantUserInfoServiceImpl extends ServiceImpl<MerchantUserInfoMapper, MerchantUserInfo> implements MerchantUserInfoService {

    @Resource
    private CurrentRequestComponent currentRequestComponent;
    @Resource
    private UserCacheService userCacheService;

    @Override
    public Result<String> merchantUserRegister(MerchantRegisterParam param) {
        if (param == null) {
            return Result.paramError();
        }
        String cacheAuthCode = userCacheService.getAuthCode(param.getPhone());
        if (!Objects.equals(cacheAuthCode, param.getAuthCode())) {
            return Result.errorInfo("验证码错误");
        }
        int merchantCount = this.count(new LambdaQueryWrapper<MerchantUserInfo>().eq(MerchantUserInfo::getPhone, param.getPhone()));
        if (merchantCount > 0) {
            return Result.errorInfo("手机号已存在，请勿重复注册");
        }
        MerchantUserInfo merchantUserInfo = new MerchantUserInfo();
        BeanUtils.copyProperties(param, merchantUserInfo);
        merchantUserInfo.setPassword(SecureUtil.md5(merchantUserInfo.getPassword()));
        if (StringUtils.isBlank(merchantUserInfo.getContactMobile())) {
            merchantUserInfo.setContactMobile(merchantUserInfo.getPhone());
        }
        boolean saveResult = this.save(merchantUserInfo);
        if (!saveResult) {
            return Result.dealError();
        }
        // 创建登录Token
        String token = currentRequestComponent.generateUserInfoToken(merchantUserInfo.getId(), merchantUserInfo.getMerchantName(), CommonConstant.MERCHANT_USER_TYPE);
        return Result.success(token);
    }

    @Override
    public Result<String> merchantUserLogin(MerchantLoginParam param) {
        if (param == null) {
            return Result.paramError();
        }
        MerchantUserInfo merchantUserInfo = this.getOne(new LambdaQueryWrapper<MerchantUserInfo>().eq(MerchantUserInfo::getPhone, param.getPhone()));
        if (merchantUserInfo == null) {
            return Result.errorInfo("用户不存在");
        }
        String encryptPassword = SecureUtil.md5(param.getPassword());
        if (!Objects.equals(encryptPassword, merchantUserInfo.getPassword())) {
            return Result.errorInfo("密码错误");
        }
        // 创建登录Token
        String token = currentRequestComponent.generateUserInfoToken(merchantUserInfo.getId(), merchantUserInfo.getMerchantName(), CommonConstant.MERCHANT_USER_TYPE);
        return Result.success(token);
    }

    @Override
    public Result<MerchantUserInfoVo> getLoginMerchantUserInfo() {
        Long merchantUserId = currentRequestComponent.getLoginUserId(CommonConstant.MERCHANT_USER_TYPE);
        if (merchantUserId == null) {
            return Result.notLoginError();
        }
        MerchantUserInfo merchantUserInfo = this.getById(merchantUserId);
        if (merchantUserInfo == null) {
            return Result.errorInfo("用户信息不存在");
        }
        MerchantUserInfoVo merchantUserInfoVo = new MerchantUserInfoVo();
        BeanUtils.copyProperties(merchantUserInfo, merchantUserInfoVo);
        merchantUserInfoVo.setStatusStr(MerchantStatusEnum.pareDesc(merchantUserInfo.getStatus()));
        merchantUserInfoVo.setLogo(AvatarUtil.createBase64Avatar(merchantUserInfo.getPhone()));
        return Result.success(merchantUserInfoVo);
    }

    @Override
    public Result<Page<MerchantUserInfoVo>> getMerchantList(MerchantListParam param) {
        if (param == null) {
            return Result.paramError();
        }
        Page<MerchantUserInfoVo> page = new Page<>(param.getCurrentPage(), param.getSize());
        IPage<MerchantUserInfo> merchantUserInfoIPage = baseMapper.getMerchantUserList(page, param);
        if (merchantUserInfoIPage == null || CollectionUtils.isEmpty(merchantUserInfoIPage.getRecords())) {
            return Result.noData();
        }
        List<MerchantUserInfoVo> merchantUserInfoVoList = merchantUserInfoIPage.getRecords().stream().map(merchantUserInfo -> {
            MerchantUserInfoVo merchantUserInfoVo = new MerchantUserInfoVo();
            BeanUtils.copyProperties(merchantUserInfo, merchantUserInfoVo);
            merchantUserInfoVo.setStatusStr(MerchantStatusEnum.pareDesc(merchantUserInfo.getStatus()));
            merchantUserInfoVo.setLogo(AvatarUtil.createBase64Avatar(merchantUserInfo.getPhone()));
            return merchantUserInfoVo;
        }).collect(Collectors.toList());
        page.setTotal(merchantUserInfoIPage.getTotal());
        page.setRecords(merchantUserInfoVoList);
        return Result.success(page);
    }

    @Override
    public Result<String> changeMerchantStatusInfo(MerchantStatusChangeParam param) {
        String adminUserName = currentRequestComponent.getLoginUserName(CommonConstant.ADMIN_USER_TYPE);
        if (StringUtils.isBlank(adminUserName)) {
            return Result.notLoginError();
        }
        MerchantStatusEnum merchantStatusEnum = MerchantStatusEnum.pare(param.getStatus());
        if (merchantStatusEnum == null) {
            return Result.errorInfo("参数状态值错误");
        }
        MerchantUserInfo merchantUserInfo = this.getById(param.getMerchantId());
        if (merchantUserInfo == null) {
            return Result.errorInfo("用户不存在");
        }
        merchantUserInfo.setStatus(param.getStatus());
        if (StringUtils.isNotBlank(param.getRemark())) {
            merchantUserInfo.setRemark(param.getRemark());
        }
        boolean result = this.saveOrUpdate(merchantUserInfo);
        if (!result) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<MerchantUserInfoVo> getMerchantUserInfo(Long merchantId) {
        MerchantUserInfo merchantUserInfo = this.getById(merchantId);
        if (merchantUserInfo == null) {
            return Result.noData();
        }
        MerchantUserInfoVo merchantUserInfoVo = new MerchantUserInfoVo();
        BeanUtils.copyProperties(merchantUserInfo, merchantUserInfoVo);
        return Result.success(merchantUserInfoVo);
    }
}
