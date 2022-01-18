package com.yidian.shop.common.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.yidian.shop.common.entity.AreaInfo;
import com.yidian.shop.common.mapper.AreaInfoMapper;
import com.yidian.shop.common.service.AreaInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.common.vo.AreaInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 省市区字典表 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-08-08
 */
@Service
@Slf4j
public class AreaInfoServiceImpl extends ServiceImpl<AreaInfoMapper, AreaInfo> implements AreaInfoService {

    private static final String CACHE_KEY = "area_cache";
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1));

    private LoadingCache<String, List<AreaInfoVo>> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .refreshAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, List<AreaInfoVo>>() {
                @Override
                public List<AreaInfoVo> load(String key) throws Exception {
                    log.info("调用load方法加载省市区数据。。。。");
                    return buildAreaInfoVoList();
                }

                @Override
                public ListenableFuture<List<AreaInfoVo>> reload(String key, List<AreaInfoVo> oldValue) throws Exception {
                    log.info("调用reload方法刷新省市区缓存数据。。。");
                    ListenableFutureTask<List<AreaInfoVo>> task = ListenableFutureTask.create(() -> buildAreaInfoVoList());
                    EXECUTOR_SERVICE.execute(task);
                    return task;
                }
            });

    @Override
    public List<AreaInfoVo> getAreaInfoList() {
        try {
            List<AreaInfoVo> list = cache.get(CACHE_KEY);
            if (CollectionUtils.isEmpty(list)) {
                return Collections.emptyList();
            }
            return list;
        } catch (Exception e) {
            log.error("获取地区信息发生异常，exception:", e);
            return this.buildAreaInfoVoList();
        }
    }

    private List<AreaInfoVo> buildAreaInfoVoList() {
        List<AreaInfo> areaList = this.list();
        if (CollectionUtils.isEmpty(areaList)) {
            return Collections.emptyList();
        }
        return getAreaChildTree(areaList, 0);
    }

    private List<AreaInfoVo> getAreaChildTree(List<AreaInfo> allAreaList, long parentId) {
        List<AreaInfoVo> areaTreeList = new ArrayList<>();
        List<AreaInfo> childAreaList = getChildCategory(allAreaList, parentId);
        if (CollectionUtils.isNotEmpty(childAreaList)) {
            for (AreaInfo area : childAreaList) {
                AreaInfoVo areaInfoTree = new AreaInfoVo();
                areaInfoTree.setName(area.getName());
                areaInfoTree.setCode(area.getCode());
                List<AreaInfoVo> subList = getAreaChildTree(allAreaList, area.getId());
                if (CollectionUtils.isNotEmpty(subList)) {
                    areaInfoTree.setSubList(subList);
                }
                areaTreeList.add(areaInfoTree);
            }
        }
        return areaTreeList;
    }

    /**
     * 过滤出父分类中的子分类
     *
     * @param allCategoryList
     * @param parentId
     * @return
     */
    private List<AreaInfo> getChildCategory(List<AreaInfo> allCategoryList, long parentId) {
        if (CollectionUtils.isEmpty(allCategoryList)) {
            return Collections.emptyList();
        }
        List<AreaInfo> categoryList = allCategoryList.stream().filter(e -> e.getParentId() == parentId).collect(Collectors.toList());
        allCategoryList.removeAll(categoryList);
        return categoryList;
    }

}
