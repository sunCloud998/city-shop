package com.yidian.shop.user;

import com.yidian.shop.mall.entity.UserInfo;
import com.yidian.shop.mall.param.UserLoginParam;
import com.yidian.shop.mall.param.UserRegisterParam;
import com.yidian.shop.mall.service.UserInfoService;
import com.yidian.shop.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 商城用户接口
 */
@RestController
@RequestMapping("/api/user/mall")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 用户注册
     *
     * @param param 用户注册请求参数
     * @return 用户注册后返回登录Token
     */
    @PostMapping("/register/v1")
    public Result<String> registerUserInfo(@RequestBody @Valid UserRegisterParam param) {
        return userInfoService.registerUserInfo(param);
    }

    /**
     * 用户登录
     *
     * @param param 登录请求参数
     * @return 登录后返回的Token
     */
    @PostMapping("/login/v1")
    public Result<String> userLogin(@RequestBody @Valid UserLoginParam param) {
        return userInfoService.userLogin(param);
    }

    /**
     * 获取登录商城的用户信息
     *
     * @return 返回当前登录的用户信息
     */
    @GetMapping("/getLoginUserInfo/v1")
    public Result<UserInfo> getLoginUserInfo() {
        return userInfoService.getLoginUserInfo();
    }


}

