package com.yidian.shop.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.admin.entity.AdminUserInfo;
import com.yidian.shop.admin.param.AdminUserAddParam;
import com.yidian.shop.admin.param.AdminUserLoginParam;
import com.yidian.shop.admin.vo.AdminUserInfoVo;
import com.yidian.shop.vo.Result;

/**
 * <p>
 * 管理员用户表 服务类
 * </p>
 *
 * @author dev
 * @since 2021-08-04
 */
public interface AdminUserInfoService extends IService<AdminUserInfo> {

    /**
     * 添加管理员
     *
     * @param param
     * @return
     */
    Result<String> addAdminUser(AdminUserAddParam param);

    /**
     * 管理登录
     *
     * @param param
     * @return
     */
    Result<String> adminUserLogin(AdminUserLoginParam param);

    /**
     * 获取登录的管理员用户信息
     *
     * @return
     */
    Result<AdminUserInfoVo> getLoginAdminUserInfo();

}
