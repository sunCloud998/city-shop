package com.yidian.shop.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.annotation.AuthorizationAdmin;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.param.*;
import com.yidian.shop.service.CartInfoService;
import com.yidian.shop.service.OrderInfoService;
import com.yidian.shop.vo.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 订单表接口列表
 *
 * @author dev
 * @since 2021-08-26
 */
@RestController
@RequestMapping("/api/order")
public class OrderInfoController {

    @Resource
    private CartInfoService cartInfoService;
    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 获取订单确认页数据
     *
     * @param cartIdList 购物车ID列表，如果为空数组返回所有选中的，不为空返回指定ID的，json格式[1,2]
     * @return 返回选中的购物车列表数据
     */
    @GetMapping("/getOrderConfirmInfo/v1")
    public Result<List<CartInfoListVo>> getOrderConfirmInfo(@RequestBody List<Long> cartIdList) {
        return cartInfoService.getOrderConfirmInfo(cartIdList);
    }

    /**
     * 提交订单
     *
     * @param param 提交订单请求参数
     * @return 返回提交结果
     */
    @PostMapping("/commitOrderInfo/v1")
    public Result<List<String>> commitOrderInfo(@RequestBody @Valid CommitOrderParam param) {
        return orderInfoService.commitOrderInfo(param);
    }

    /**
     * 取消订单
     *
     * @param param 取消订单请求参数
     * @return 返回处理结果
     */
    @PostMapping("/cancelOrder/v1")
    public Result<String> cancelOrder(@RequestBody @Valid CancelOrderParam param) {
        return orderInfoService.cancelOrder(param);
    }

    /**
     * 删除订单
     *
     * @param param 删除订单请求参数
     * @return 返回处理结果
     */
    @PostMapping("/delOrder/v1")
    public Result<String> delOrder(@RequestBody @Valid DelOrderParam param) {
        return orderInfoService.delOrder(param);
    }

    /**
     * 关闭超时未支付订单
     *
     * @return
     */
    @GetMapping("/closeOrder/v1")
    public Result<String> closeOrder() {
        return orderInfoService.closeOrder();
    }

    /**
     * 订单发货
     *
     * @param param 订单发货请求参数
     * @return 返回处理结果
     */
    @PostMapping("/orderDelivery/v1")
    public Result<String> orderDelivery(@RequestBody @Valid OrderDeliveryParam param) {
        return orderInfoService.orderDelivery(param);
    }

    /**
     * 订单收货
     *
     * @param orderNo 订单号
     * @return 返回处理结果
     */
    @GetMapping("/orderReceive/{orderNo}")
    public Result<String> orderReceive(@PathVariable("orderNo") String orderNo) {
        return orderInfoService.orderReceive(orderNo);
    }

    /**
     * 订单自动完成
     *
     * @return
     */
    @GetMapping("/orderAutoComplete/v1")
    public Result<String> orderAutoComplete() {
        return orderInfoService.orderAutoComplete();
    }

    /**
     * 订单列表查询
     *
     * @param param 订单列表查询请求参数
     * @return 返回订单列表数据
     */
    @PostMapping("/getOrderList/v1")
    public Result<Page<OrderInfoVo>> getOrderList(@RequestBody OrderListParam param) {
        return orderInfoService.getOrderList(param);
    }

    /**
     * 订单详情查询
     *
     * @param orderNo 订单号
     * @return 返回订单详情数据
     */
    @GetMapping("/getOrderDetail/{orderNo}")
    public Result<OrderDetailVo> getOrderDetail(@PathVariable("orderNo") String orderNo) {
        return orderInfoService.getOrderDetail(orderNo);
    }

    /**
     * 获取订单进程
     *
     * @param orderNo 订单号
     * @return 返回订单进程列表
     */
    @GetMapping("/getOrderProcessInfo/{orderNo}")
    public Result<List<OrderProcessVo>> getOrderProcessInfo(@PathVariable("orderNo") String orderNo) {
        List<OrderProcessVo> orderProcessVoList = orderInfoService.getOrderProcessInfo(orderNo);
        if (CollectionUtils.isEmpty(orderProcessVoList)) {
            return Result.noData();
        }
        return Result.success(orderProcessVoList);
    }

    /**
     * 获取收银台展示信息
     *
     * @param orderNoList 订单号列表,json格式：["1","2"]
     * @return 返回收银台展示信息
     */
    @AuthorizationAdmin(userType = CommonConstant.MALL_USER_TYPE)
    @PostMapping("/getCashierInfo/v1")
    public Result<CashierInfoVo> getCashierInfo(@RequestBody List<String> orderNoList) {
        return orderInfoService.getCashierInfo(orderNoList);
    }

}

