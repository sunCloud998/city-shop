package com.yidian.shop.mall.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.common.service.impl.UserCacheServiceImpl;
import com.yidian.shop.component.CurrentRequestComponent;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.mall.entity.UserInfo;
import com.yidian.shop.mall.mapper.UserInfoMapper;
import com.yidian.shop.mall.param.UserLoginParam;
import com.yidian.shop.mall.param.UserRegisterParam;
import com.yidian.shop.mall.service.UserInfoService;
import com.yidian.shop.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-07-30
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private CurrentRequestComponent currentRequestComponent;
    @Resource
    private UserCacheServiceImpl userCacheService;

    @Override
    public Result<String> registerUserInfo(UserRegisterParam param) {
        if (param == null) {
            return Result.paramError();
        }
        String cacheAuthCode = userCacheService.getAuthCode(param.getPhone());
        if (!Objects.equals(cacheAuthCode, param.getAuthCode())) {
            return Result.errorInfo("验证码错误");
        }
        if (!Objects.equals(param.getPassword(), param.getConfirmPassword())) {
            return Result.errorInfo("两次输入的密码不一致，请重新输入");
        }
        int userCount = this.count(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getPhone, param.getPhone()));
        if (userCount > 0) {
            return Result.errorInfo("手机号已存在，请勿重复注册");
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(param, userInfo);
        // 加密密码
        userInfo.setPassword(SecureUtil.md5(param.getPassword()));
        boolean saveResult = this.save(userInfo);
        if (!saveResult) {
            return Result.dealError();
        }
        // 创建登录Token
        String token = currentRequestComponent.generateUserInfoToken(userInfo.getId(), userInfo.getUserName(), CommonConstant.MALL_USER_TYPE);
        return Result.success(token);
    }

    @Override
    public Result<String> userLogin(UserLoginParam param) {
        UserInfo userInfo = this.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getPhone, param.getPhone()));
        if (userInfo == null) {
            return Result.errorInfo("用户不存在");
        }
        String encryptPassword = SecureUtil.md5(param.getPassword());
        if (!Objects.equals(encryptPassword, userInfo.getPassword())) {
            return Result.errorInfo("登录密码错误");
        }
        // 创建登录Token
        String token = currentRequestComponent.generateUserInfoToken(userInfo.getId(), userInfo.getUserName(), CommonConstant.MALL_USER_TYPE);
        return Result.success(token);
    }

    @Override
    public Result<UserInfo> getLoginUserInfo() {
        Long userId = currentRequestComponent.getLoginUserId(CommonConstant.MALL_USER_TYPE);
        if (userId == null) {
            return Result.notLoginError();
        }
        UserInfo userInfo = this.getById(userId);
        if (userInfo == null) {
            return Result.errorInfo("用户信息不存在");
        }
        return Result.success(userInfo);
    }
}
