package com.yidian.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.entity.RefundInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.param.*;
import com.yidian.shop.vo.RefundDetailVo;
import com.yidian.shop.vo.RefundInfoVo;
import com.yidian.shop.vo.RefundReasonTagVo;
import com.yidian.shop.vo.Result;

import java.util.List;

/**
 * <p>
 * 订单退货申请 服务类
 * </p>
 *
 * @author dev
 * @since 2021-08-26
 */
public interface RefundInfoService extends IService<RefundInfo> {

    /**
     * 获取退款原因列表
     *
     * @return
     */
    Result<List<RefundReasonTagVo>> getRefundReasonTagInfo();

    /**
     * 退款申请
     *
     * @param param
     * @return
     */
    Result<String> refundApply(RefundApplyParam param);

    /**
     * 取消退款
     *
     * @param refundId
     * @return
     */
    Result<String> refundCancel(Long refundId);

    /**
     * 退款审核
     *
     * @param param
     * @return
     */
    Result<String> refundExamine(RefundExamineParam param);

    /**
     * 退货退款商品退回
     *
     * @param param
     * @return
     */
    Result<String> refundBack(RefundBackParam param);

    /**
     * 退货商品收货
     *
     * @param param
     * @return
     */
    Result<String> refundReceive(RefundReceiveParam param);

    /**
     * 退款列表查询
     *
     * @param param
     * @return
     */
    Result<Page<RefundInfoVo>> getRefundList(RefundListParam param);

    /**
     * 退款详情查询
     *
     * @param refundId
     * @return
     */
    Result<RefundDetailVo> getRefundDetail(Long refundId);
}
