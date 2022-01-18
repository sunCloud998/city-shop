package com.yidian.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.param.*;
import com.yidian.shop.vo.*;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author dev
 * @since 2021-08-26
 */
public interface OrderInfoService extends IService<OrderInfo> {

    Result<List<String>> commitOrderInfo(CommitOrderParam param);

    Result<String> cancelOrder(CancelOrderParam param);

    Result<String> delOrder(DelOrderParam param);

    Result<String> closeOrder();

    Result<Page<OrderInfoVo>> getOrderList(OrderListParam param);

    Result<OrderDetailVo> getOrderDetail(String orderNo);

    List<OrderProcessVo> getOrderProcessInfo(String orderNo);

    Result<String> orderDelivery(OrderDeliveryParam param);

    Result<String> orderReceive(String orderNo);

    Result<String> orderAutoComplete();

    Result<CashierInfoVo> getCashierInfo(List<String> orderNoList);
}
