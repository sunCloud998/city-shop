package com.yidian.shop.service;

import com.yidian.shop.entity.PayInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.param.PayParam;
import com.yidian.shop.vo.Result;

/**
 * <p>
 * 支付记录信息表 服务类
 * </p>
 *
 * @author dev
 * @since 2021-09-10
 */
public interface PayInfoService extends IService<PayInfo> {

    /**
     * 支付宝支付
     *
     * @param param
     * @return
     */
    Result<String> aliPay(PayParam param);
}
