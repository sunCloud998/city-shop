package com.yidian.shop.service;

import com.yidian.shop.entity.CartInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.param.AddCartInfoParam;
import com.yidian.shop.param.UpdateCartInfoParam;
import com.yidian.shop.vo.CartInfoListVo;
import com.yidian.shop.vo.Result;

import java.util.List;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author dev
 * @since 2021-08-26
 */
public interface CartInfoService extends IService<CartInfo> {

    Result<Long> addCartInfo(AddCartInfoParam param);

    Result<String> updateCartInfo(UpdateCartInfoParam param);

    Result<String> delCartInfo(Long cartId);

    Result<List<CartInfoListVo>> getCartInfoList();

    Result<List<CartInfoListVo>> getOrderConfirmInfo(List<Long> cartIdList);

    Result<String> clearCart();
}
