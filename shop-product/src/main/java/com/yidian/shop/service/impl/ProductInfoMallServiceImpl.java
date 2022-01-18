package com.yidian.shop.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.yidian.shop.enums.ProductShowTypeEnum;
import com.yidian.shop.mapper.ProductInfoMapper;
import com.yidian.shop.param.ProductMallParam;
import com.yidian.shop.service.ProductInfoMallService;
import com.yidian.shop.service.ProductInfoService;
import com.yidian.shop.vo.ProductAllInfoVo;
import com.yidian.shop.vo.ProductIndexVo;
import com.yidian.shop.vo.ProductInfoVo;
import com.yidian.shop.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @function:
 * @description: ProductInfoMallServiceImpl.java
 * @date: 2021/08/23
 * @author: sunfayun
 * @version: 1.0
 */
@Service
@Slf4j
public class ProductInfoMallServiceImpl implements ProductInfoMallService {

    private final static Integer THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    private final static ListeningExecutorService SERVICE = MoreExecutors.listeningDecorator(new ThreadPoolExecutor(THREAD_COUNT, THREAD_COUNT * 2, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1)));

    @Resource
    private ProductInfoMapper productInfoMapper;
    @Resource
    private ProductInfoService productInfoService;

    @Override
    public Result<ProductIndexVo> getIndexProductInfo() {
        try {
            // 查询新品
            ListenableFuture<List<ProductInfoVo>> newProductFuture = SERVICE.submit(() -> getIndexProductVoList(ProductShowTypeEnum.NEW_AREA.getCode()));
            // 查询人气
            ListenableFuture<List<ProductInfoVo>> hotProductFuture = SERVICE.submit(() -> getIndexProductVoList(ProductShowTypeEnum.HOT_AREA.getCode()));
            // 查询默认
            ListenableFuture<List<ProductInfoVo>> defaultProductFuture = SERVICE.submit(() -> getIndexProductVoList(ProductShowTypeEnum.DEFAULT_AREA.getCode()));

            ProductIndexVo indexProductVo = new ProductIndexVo();
            indexProductVo.setNewProductList(newProductFuture.get(10, TimeUnit.SECONDS));
            indexProductVo.setHotProductList(hotProductFuture.get(10, TimeUnit.SECONDS));
            indexProductVo.setDefaultProductList(defaultProductFuture.get(10, TimeUnit.SECONDS));
            return Result.success(indexProductVo);
        } catch (Exception e) {
            log.error("获取首页区域数据异常，exception:", e);
            return Result.errorInfo("服务异常，稍后再试");
        }
    }

    private List<ProductInfoVo> getIndexProductVoList(Integer type) {
        try {
            IPage<ProductInfoVo> productInfoVoIPage = productInfoMapper.getIndexProductInfoList(new Page<>(1, 10), type);
            if (productInfoVoIPage == null || CollectionUtils.isEmpty(productInfoVoIPage.getRecords())) {
                return Collections.emptyList();
            }
            return productInfoVoIPage.getRecords();
        } catch (Exception e) {
            log.error("查询首页区域商品信息异常，type:{}, exception:", type, e);
            return Collections.emptyList();
        }
    }

    @Override
    public Result<Page<ProductInfoVo>> getConditionProductInfo(ProductMallParam param) {
        Page<ProductInfoVo> productInfoVoPage = productInfoMapper.getProductInfoListByCondition(new Page<>(param.getCurrentPage(), param.getSize()), param);
        if (productInfoVoPage == null || CollectionUtils.isEmpty(productInfoVoPage.getRecords())) {
            return Result.noData();
        }
        return Result.success(productInfoVoPage);
    }

    @Override
    public Result<ProductAllInfoVo> getProductDetailInfo(Long id) {
        return productInfoService.getProductAllInfo(id);
    }
}
