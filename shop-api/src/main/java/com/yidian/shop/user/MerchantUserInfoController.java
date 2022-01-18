package com.yidian.shop.user;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.merchant.entity.MerchantUserInfo;
import com.yidian.shop.merchant.param.MerchantListParam;
import com.yidian.shop.merchant.param.MerchantLoginParam;
import com.yidian.shop.merchant.param.MerchantRegisterParam;
import com.yidian.shop.merchant.param.MerchantStatusChangeParam;
import com.yidian.shop.merchant.service.MerchantUserInfoService;
import com.yidian.shop.merchant.vo.MerchantUserInfoVo;
import com.yidian.shop.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 商家用户接口
 *
 * @author dev
 * @since 2021-08-03
 */
@RestController
@RequestMapping("/api/user/merchant")
public class MerchantUserInfoController {

    @Resource
    private MerchantUserInfoService merchantUserInfoService;

    /**
     * 供应商注册
     *
     * @param param 供应商注册请求参数
     * @return 返回用户信息Token
     */
    @PostMapping("/register/v1")
    public Result<String> merchantUserRegister(@RequestBody @Valid MerchantRegisterParam param) {
        return merchantUserInfoService.merchantUserRegister(param);
    }

    /**
     * 供应商登录
     *
     * @param param 供应商登录请求参数
     * @return 返回用户信息Token
     */
    @PostMapping("/login/v1")
    public Result<String> merchantUserLogin(@RequestBody @Valid MerchantLoginParam param) {
        return merchantUserInfoService.merchantUserLogin(param);
    }

    /**
     * 获取登录的供应商信息
     *
     * @return 返回当前登录的供应商信息
     */
    @GetMapping("/getLoginMerchantUserInfo/v1")
    public Result<MerchantUserInfoVo> getLoginMerchantUserInfo() {
        return merchantUserInfoService.getLoginMerchantUserInfo();
    }

    /**
     * 根据供应商ID查询供应商信息
     *
     * @param merchantId 供应商ID
     * @return 返回供应商信息
     */
    @GetMapping("/getMerchantUserInfo/{merchantId}")
    public Result<MerchantUserInfoVo> getMerchantUserInfo(@PathVariable("merchantId") Long merchantId) {
        return merchantUserInfoService.getMerchantUserInfo(merchantId);
    }

    /**
     * 供应商列表查询
     *
     * @param param 列表分页查询筛选参数
     * @return 分页返回供应商列表
     */
    @PostMapping("/getMerchantList/v1")
    public Result<Page<MerchantUserInfoVo>> getMerchantList(@RequestBody MerchantListParam param) {
        return merchantUserInfoService.getMerchantList(param);
    }

    /**
     * 供应商状态变更
     * 0-待审核；1-审核通过；2-审核拒绝；3-禁用
     *
     * @param param 状态变更请求参数
     * @return 变更结果
     */
    @PostMapping("/changeMerchantStatusInfo/v1")
    Result<String> changeMerchantStatusInfo(@RequestBody @Valid MerchantStatusChangeParam param) {
        return merchantUserInfoService.changeMerchantStatusInfo(param);
    }
}

