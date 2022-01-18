package com.yidian.shop.merchant.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.merchant.entity.MerchantUserInfo;
import com.yidian.shop.merchant.param.MerchantListParam;
import com.yidian.shop.merchant.param.MerchantLoginParam;
import com.yidian.shop.merchant.param.MerchantRegisterParam;
import com.yidian.shop.merchant.param.MerchantStatusChangeParam;
import com.yidian.shop.merchant.vo.MerchantUserInfoVo;
import com.yidian.shop.vo.Result;

/**
 * <p>
 * 商家用户表 服务类
 * </p>
 *
 * @author dev
 * @since 2021-08-04
 */
public interface MerchantUserInfoService extends IService<MerchantUserInfo> {

    /**
     * 供应商注册
     *
     * @param param
     * @return
     */
    Result<String> merchantUserRegister(MerchantRegisterParam param);

    /**
     * 供应商登录
     *
     * @param param
     * @return
     */
    Result<String> merchantUserLogin(MerchantLoginParam param);

    /**
     * 获取登录的供应商信息
     *
     * @return
     */
    Result<MerchantUserInfoVo> getLoginMerchantUserInfo();

    /**
     * 查询供应商列表
     *
     * @param param
     * @return
     */
    Result<Page<MerchantUserInfoVo>> getMerchantList(MerchantListParam param);

    /**
     * 供应商状态变更
     *
     * @param param
     * @return
     */
    Result<String> changeMerchantStatusInfo(MerchantStatusChangeParam param);

    /**
     * 根据ID查询供应商信息
     *
     * @param merchantId
     * @return
     */
    Result<MerchantUserInfoVo> getMerchantUserInfo(Long merchantId);
}
