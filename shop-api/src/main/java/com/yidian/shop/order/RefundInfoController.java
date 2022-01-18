package com.yidian.shop.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.param.*;
import com.yidian.shop.service.RefundInfoService;
import com.yidian.shop.vo.RefundDetailVo;
import com.yidian.shop.vo.RefundInfoVo;
import com.yidian.shop.vo.RefundReasonTagVo;
import com.yidian.shop.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 退款相关接口
 *
 * @author dev
 * @since 2021-08-26
 */
@RestController
@RequestMapping("/api/refund")
public class RefundInfoController {

    @Resource
    private RefundInfoService refundInfoService;

    /**
     * 获取退款原因列表
     *
     * @return 返回退款原因列表
     */
    @GetMapping("/getRefundReasonTagInfo/v1")
    public Result<List<RefundReasonTagVo>> getRefundReasonTagInfo() {
        return refundInfoService.getRefundReasonTagInfo();
    }

    /**
     * 退款申请
     *
     * @param param 退款申请请求参数
     * @return 返回申请结果
     */
    @PostMapping("/refundApply/v1")
    public Result<String> refundApply(@RequestBody @Valid RefundApplyParam param) {
        return refundInfoService.refundApply(param);
    }

    /**
     * 取消退款
     *
     * @param refundId 退款记录ID
     * @return
     */
    @GetMapping("/refundCancel/{refundId}")
    public Result<String> refundCancel(@PathVariable("refundId") Long refundId) {
        return refundInfoService.refundCancel(refundId);
    }

    /**
     * 退款审核
     *
     * @param param 退款审核请求参数
     * @return 审核结果
     */
    @PostMapping("/refundExamine/v1")
    public Result<String> refundExamine(@RequestBody @Valid RefundExamineParam param) {
        return refundInfoService.refundExamine(param);
    }

    /**
     * 退货退款商品退回
     *
     * @param param 商品退回请求参数
     * @return 返回处理结果
     */
    @PostMapping("/refundBack/v1")
    public Result<String> refundBack(@RequestBody @Valid RefundBackParam param) {
        return refundInfoService.refundBack(param);
    }

    /**
     * 退货退款退回商品收货
     *
     * @param param 退款收货请求参数
     * @return 返回处理结果
     */
    @GetMapping("/refundReceive/v1")
    public Result<String> refundReceive(@RequestBody @Valid RefundReceiveParam param) {
        return refundInfoService.refundReceive(param);
    }

    /**
     * 退款列表查询
     *
     * @param param 列表查询请求参数
     * @return 返回列表结果
     */
    @PostMapping("/getRefundList/v1")
    public Result<Page<RefundInfoVo>> getRefundList(@RequestBody RefundListParam param) {
        return refundInfoService.getRefundList(param);
    }

    /**
     * 退款详情查询
     *
     * @param refundId 退款单ID
     * @return 返回退款详情
     */
    @GetMapping("/getRefundDetail/{refundId}")
    public Result<RefundDetailVo> getRefundDetail(@PathVariable("refundId") Long refundId) {
        return refundInfoService.getRefundDetail(refundId);
    }


}

