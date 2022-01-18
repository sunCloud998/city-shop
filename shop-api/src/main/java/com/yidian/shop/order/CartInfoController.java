package com.yidian.shop.order;


import com.yidian.shop.param.AddCartInfoParam;
import com.yidian.shop.param.UpdateCartInfoParam;
import com.yidian.shop.service.CartInfoService;
import com.yidian.shop.vo.CartInfoListVo;
import com.yidian.shop.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 购物车表接口列表
 *
 * @author dev
 * @since 2021-08-26
 */
@RestController
@RequestMapping("/api/cart/mall")
public class CartInfoController {

    @Resource
    private CartInfoService cartInfoService;

    /**
     * 添加购物车(直接购买也调用该接口添加)
     *
     * @param param 添加购物车请求参数
     * @return 添加成功返回购物车ID
     */
    @PostMapping("/addCartInfo/v1")
    public Result<Long> addCartInfo(@RequestBody @Valid AddCartInfoParam param) {
        return cartInfoService.addCartInfo(param);
    }

    /**
     * 修改购物车
     *
     * @param param 修改购物车请求参数
     * @return 返回修改成功或者失败
     */
    @PostMapping("/updateCartInfo/v1")
    public Result<String> updateCartInfo(@RequestBody @Valid UpdateCartInfoParam param) {
        return cartInfoService.updateCartInfo(param);
    }

    /**
     * 删除购物车商品
     *
     * @param cartId 购物车ID
     * @return 返回删除成功或者失败
     */
    @GetMapping("/delCartInfo/{cartId}")
    public Result<String> delCartInfo(@PathVariable("cartId") Long cartId) {
        return cartInfoService.delCartInfo(cartId);
    }

    /**
     * 获取购物车商品列表
     *
     * @return 按商家分组返回购物车商品列表
     */
    @GetMapping("/getCartInfoList/v1")
    public Result<List<CartInfoListVo>> getCartInfoList() {
        return cartInfoService.getCartInfoList();
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @GetMapping("/clearCart/v1")
    public Result<String> clearCart() {
        return cartInfoService.clearCart();
    }
}

