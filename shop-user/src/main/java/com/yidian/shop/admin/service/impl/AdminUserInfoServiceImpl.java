package com.yidian.shop.admin.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yidian.shop.admin.entity.AdminUserInfo;
import com.yidian.shop.admin.mapper.AdminUserInfoMapper;
import com.yidian.shop.admin.param.AdminUserAddParam;
import com.yidian.shop.admin.param.AdminUserLoginParam;
import com.yidian.shop.admin.service.AdminUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.admin.vo.AdminUserInfoVo;
import com.yidian.shop.component.CurrentRequestComponent;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.utils.AvatarUtil;
import com.yidian.shop.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 管理员用户表 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-08-04
 */
@Service
@Slf4j
public class AdminUserInfoServiceImpl extends ServiceImpl<AdminUserInfoMapper, AdminUserInfo> implements AdminUserInfoService {

    @Resource
    private CurrentRequestComponent currentRequestComponent;

    @Override
    public Result<String> addAdminUser(AdminUserAddParam param) {
        if (param == null) {
            return Result.paramError();
        }
        int userCount = this.count(new LambdaQueryWrapper<AdminUserInfo>().eq(AdminUserInfo::getPhone, param.getPhone()));
        if (userCount > 0) {
            return Result.errorInfo("手机号已存在，请勿重复添加");
        }
        if (StringUtils.isBlank(param.getPassword())) {
            param.setPassword(param.getPhone().substring(5));
        }
        AdminUserInfo adminUserInfo = new AdminUserInfo();
        BeanUtils.copyProperties(param, adminUserInfo);
        adminUserInfo.setPassword(SecureUtil.md5(adminUserInfo.getPassword()));
        boolean saveResult = this.save(adminUserInfo);
        if (!saveResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<String> adminUserLogin(AdminUserLoginParam param) {
        if (param == null) {
            return Result.paramError();
        }
        AdminUserInfo adminUserInfo = this.getOne(new LambdaQueryWrapper<AdminUserInfo>().eq(AdminUserInfo::getPhone, param.getPhone()));
        if (adminUserInfo == null) {
            return Result.errorInfo("用户不存在");
        }
        String encryptPassword = SecureUtil.md5(param.getPassword());
        if (!Objects.equals(encryptPassword, adminUserInfo.getPassword())) {
            return Result.errorInfo("登录密码错误");
        }
        // 创建登录Token
        String token = currentRequestComponent.generateUserInfoToken(adminUserInfo.getId(), adminUserInfo.getUserName(), CommonConstant.ADMIN_USER_TYPE);
        return Result.success(token);
    }

    @Override
    public Result<AdminUserInfoVo> getLoginAdminUserInfo() {
        Long adminUserId = currentRequestComponent.getLoginUserId(CommonConstant.ADMIN_USER_TYPE);
        if (adminUserId == null) {
            return Result.notLoginError();
        }
        AdminUserInfo adminUserInfo = this.getById(adminUserId);
        if (adminUserInfo == null) {
            return Result.errorInfo("用户信息不存在");
        }
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo();
        BeanUtils.copyProperties(adminUserInfo, adminUserInfoVo);
        adminUserInfoVo.setIcon(AvatarUtil.createBase64Avatar(adminUserInfo.getPhone()));
        return Result.success(adminUserInfoVo);
    }
}
