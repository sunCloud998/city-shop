package com.yidian.shop.service.impl;

import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.ijpay.alipay.AliPayApi;
import com.yidian.shop.config.yml.AliPayConfig;
import com.yidian.shop.entity.PayInfo;
import com.yidian.shop.mapper.PayInfoMapper;
import com.yidian.shop.param.PayParam;
import com.yidian.shop.service.PayInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.utils.GenerateNoUtil;
import com.yidian.shop.vo.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 支付记录信息表 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-09-10
 */
@Service
public class PayInfoServiceImpl extends ServiceImpl<PayInfoMapper, PayInfo> implements PayInfoService {

    @Resource
    private AliPayConfig aliPayConfig;

    @Override
    public Result<String> aliPay(PayParam param) {
        String body = "我是测试数据-By Javen";
        String subject = "Javen Wap支付测试";
        String totalAmount = "1";
        String passBackParams = "1";
        String returnUrl = aliPayConfig.getDomain() + "/payNotify/v1";
        String notifyUrl = aliPayConfig.getDomain() + "/returnNotify/v1";

        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setPassbackParams(passBackParams);
        model.setOutTradeNo(GenerateNoUtil.generateTradeNo());
        model.setProductCode("QUICK_WAP_PAY");

        try {
            String result = AliPayApi.wapPayStr(model, returnUrl, notifyUrl);
            System.err.println("====>" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }
}
