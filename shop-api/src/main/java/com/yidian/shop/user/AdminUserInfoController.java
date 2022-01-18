package com.yidian.shop.user;


import com.yidian.shop.admin.entity.AdminUserInfo;
import com.yidian.shop.admin.param.AdminUserAddParam;
import com.yidian.shop.admin.param.AdminUserLoginParam;
import com.yidian.shop.admin.service.AdminUserInfoService;
import com.yidian.shop.admin.vo.AdminUserInfoVo;
import com.yidian.shop.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 管理员用户接口
 *
 * @author dev
 * @since 2021-08-03
 */
@RestController
@RequestMapping("/api/user/admin")
public class AdminUserInfoController {

    @Resource
    private AdminUserInfoService adminUserInfoService;

    /**
     * 添加管理员
     *
     * @param param 添加管理请求参数
     * @return 返回添加结果
     */
    @PostMapping("/addAdminUser/v1")
    public Result<String> addAdminUser(@RequestBody @Valid AdminUserAddParam param) {
        return adminUserInfoService.addAdminUser(param);
    }

    /**
     * 管理登录
     *
     * @param param 管理员登录请求参数
     * @return 返回登录Token
     */
    @PostMapping("/login/v1")
    public Result<String> adminUserLogin(@RequestBody @Valid AdminUserLoginParam param) {
        return adminUserInfoService.adminUserLogin(param);
    }

    /**
     * 获取登录的管理员用户信息
     *
     * @return 返回当前登录的管理员用户信息
     */
    @GetMapping("/getLoginAdminUserInfo/v1")
    public Result<AdminUserInfoVo> getLoginAdminUserInfo() {
        return adminUserInfoService.getLoginAdminUserInfo();
    }

}

