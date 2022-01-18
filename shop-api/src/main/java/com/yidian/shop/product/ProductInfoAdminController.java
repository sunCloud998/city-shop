package com.yidian.shop.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.param.ProductChangeStatusParam;
import com.yidian.shop.param.ProductListParam;
import com.yidian.shop.param.ProductSaveOrUpdateParam;
import com.yidian.shop.service.ProductInfoService;
import com.yidian.shop.vo.ProductAllInfoVo;
import com.yidian.shop.vo.ProductInfoVo;
import com.yidian.shop.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 管理员商品相关接口
 *
 * @function:
 * @description: ProductInfoAdminController.java
 * @date: 2021/08/22
 * @author: sunfayun
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/product/admin")
public class ProductInfoAdminController {

    @Resource
    private ProductInfoService productInfoService;

    /**
     * 添加或者修改商品
     *
     * @param param 请求参数
     * @return
     */
    @PostMapping("/saveOrUpdate/v1")
    public Result<String> saveOrUpdateProduct(@RequestBody @Valid ProductSaveOrUpdateParam param) {
        return productInfoService.saveOrUpdateProductInfo(param);
    }

    /**
     * 查询商品所有信息，更新或者查看商品详情可以调用该方法
     *
     * @param id 商品ID
     * @return 返回商品基础信息，商品详情、商品参数、商品规格、商品sku
     */
    @GetMapping("/getProductAllInfo/{id}")
    public Result<ProductAllInfoVo> getProductAllInfo(@PathVariable("id") Long id) {
        return productInfoService.getProductAllInfo(id);
    }

    /**
     * 商品列表查询
     *
     * @param param 商品列表筛选参数
     * @return 商品基础信息列表返回结果
     */
    @PostMapping("/getProductInfoList/v1")
    public Result<Page<ProductInfoVo>> getProductInfoList(@RequestBody ProductListParam param) {
        return productInfoService.getProductInfoList(param);
    }

    /**
     * 商品状态变更接口
     *
     * @param param 商品上、下架请求参数
     * @return 返回操作结果
     */
    @PostMapping("/productChangeStatus/v1")
    public Result<String> productChangeStatus(@RequestBody @Valid ProductChangeStatusParam param) {
        return productInfoService.productChangeStatus(param);
    }

}
