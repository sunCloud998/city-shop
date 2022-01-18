package com.yidian.shop.product;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.param.ProductCategoryListParam;
import com.yidian.shop.param.ProductCategoryParam;
import com.yidian.shop.service.ProductCategoryService;
import com.yidian.shop.vo.ProductCategoryTreeVo;
import com.yidian.shop.vo.ProductCategoryVo;
import com.yidian.shop.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 产品分类接口
 *
 * @author dev
 * @since 2021-08-13
 */
@RestController
@RequestMapping("/api/product/category")
public class ProductCategoryController {

    @Resource
    private ProductCategoryService productCategoryService;

    /**
     * 添加或者修改产品分类
     *
     * @param param 添加或修改产品分类请求参数，id不为空默认为修改
     * @return 返回成功或者失败
     */
    @PostMapping("/addOrUpdate/v1")
    public Result<String> addOrUpdateProductCategory(@RequestBody @Valid ProductCategoryParam param) {
        return productCategoryService.addOrUpdateProductCategory(param);
    }

    /**
     * 获取产品分类列表
     *
     * @param param 请求参数，parentId必填，第一级为0
     * @return 返回产品分类列表
     */
    @PostMapping("/getProductCategoryList/v1")
    public Result<Page<ProductCategoryVo>> getProductCategoryList(@RequestBody @Valid ProductCategoryListParam param) {
        return productCategoryService.getProductCategoryList(param);
    }

    /**
     * 删除产品分类
     *
     * @param id 产品分类ID
     * @return
     */
    @GetMapping("/delProductCategory/{id}")
    public Result<String> delProductCategory(@PathVariable("id") Long id) {
        return productCategoryService.delProductCategory(id);
    }

    /**
     * 根据分类ID获取对应的分类信息
     *
     * @param id 需要获取分类信息的ID
     * @return 返回分类信息
     */
    @GetMapping("/getProductCategory/{id}")
    public Result<ProductCategoryVo> getProductCategoryById(@PathVariable("id") Long id) {
        return productCategoryService.getProductCategoryById(id);
    }

    /**
     * 获取树形结构的产品分类列表
     *
     * @return
     */
    @GetMapping("/getProductCategoryTree/v1")
    public Result<List<ProductCategoryTreeVo>> getProductCategoryTree() {
        return productCategoryService.getProductCategoryTree();
    }

}

