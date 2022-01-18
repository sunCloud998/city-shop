package com.yidian.shop.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.mall.entity.UserInfo;
import com.yidian.shop.mall.param.UserLoginParam;
import com.yidian.shop.mall.param.UserRegisterParam;
import com.yidian.shop.vo.Result;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author dev
 * @since 2021-07-30
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 用户注册
     *
     * @param param
     * @return
     */
    Result<String> registerUserInfo(UserRegisterParam param);

    /**
     * 用户登录
     *
     * @param param
     * @return
     */
    Result<String> userLogin(UserLoginParam param);

    /**
     * 获取商城登录用户信息
     *
     * @return
     */
    Result<UserInfo> getLoginUserInfo();

}
