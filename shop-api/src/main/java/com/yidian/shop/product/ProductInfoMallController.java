package com.yidian.shop.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.param.ProductMallParam;
import com.yidian.shop.service.ProductInfoMallService;
import com.yidian.shop.vo.ProductAllInfoVo;
import com.yidian.shop.vo.ProductIndexVo;
import com.yidian.shop.vo.ProductInfoVo;
import com.yidian.shop.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 商城商品相关接口
 *
 * @function:
 * @description: ProductInfoMallController.java
 * @date: 2021/08/23
 * @author: sunfayun
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/product/mall")
public class ProductInfoMallController {

    @Resource
    private ProductInfoMallService productInfoMallService;

    /**
     * 获取首页默认数据
     *
     * @return 返回新品、推荐、默认列表的数据，每个类型返回10条
     */
    @GetMapping("/getIndexProductInfo/v1")
    public Result<ProductIndexVo> getIndexProductInfo() {
        return productInfoMallService.getIndexProductInfo();
    }

    /**
     * 分页查询指定条件商品列表
     *
     * @param param 筛选条件请求参数
     * @return 返回对应区域的商品列表
     */
    @PostMapping("/getAreaProductInfo/v1")
    public Result<Page<ProductInfoVo>> getConditionProductInfo(@RequestBody ProductMallParam param) {
        return productInfoMallService.getConditionProductInfo(param);
    }

    /**
     * 获取商城商品详情
     *
     * @param id 商品ID
     * @return 商品详细信息
     */
    @GetMapping("/getProductDetailInfo/{id}")
    public Result<ProductAllInfoVo> getProductDetailInfo(@PathVariable("id") Long id) {
        return productInfoMallService.getProductDetailInfo(id);
    }

}
