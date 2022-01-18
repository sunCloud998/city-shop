package com.yidian.shop.product;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.param.ProductBrandListParam;
import com.yidian.shop.param.ProductBrandParam;
import com.yidian.shop.service.ProductBrandService;
import com.yidian.shop.vo.ProductBrandVo;
import com.yidian.shop.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 商品品牌管理接口
 *
 * @author dev
 * @since 2021-08-15
 */
@RestController
@RequestMapping("/api/product/brand")
public class ProductBrandController {

    @Resource
    private ProductBrandService productBrandService;

    /**
     * 添加或者修改商品品牌
     *
     * @param param 请求参数，ID不为空为更新
     * @return 返回请求结果
     */
    @PostMapping("/saveOrUpdate/v1")
    public Result<String> saveOrUpdateProductBrand(@RequestBody @Valid ProductBrandParam param) {
        return productBrandService.saveOrUpdateProductBrand(param);
    }

    /**
     * 根据ID删除商品品牌
     *
     * @param id 需要删除的商品品牌ID
     * @return 返回处理结果
     */
    @GetMapping("/delProductBrand/{id}")
    public Result<String> delProductBrand(@PathVariable("id") Long id) {
        return productBrandService.delProductBrand(id);
    }

    /**
     * 根据ID查询商品品牌信息
     *
     * @param id 需要查询的商品品牌对应的ID
     * @return 返回商品品牌信息
     */
    @GetMapping("/getProductBrand/{id}")
    public Result<ProductBrandVo> getProductBrandById(@PathVariable("id") Long id) {
        return productBrandService.getProductBrandById(id);
    }

    /**
     * 分页获取商品品牌列表
     *
     * @param param 查询参数
     * @return 返回商品品牌列表
     */
    @PostMapping("/getProductBrandList/v1")
    public Result<Page<ProductBrandVo>> getProductBrandList(@RequestBody ProductBrandListParam param) {
        return productBrandService.getProductBrandList(param);
    }

}

