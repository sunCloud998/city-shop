package com.yidian.shop.common;

import com.yidian.shop.common.service.AreaInfoService;
import com.yidian.shop.common.vo.AreaInfoVo;
import com.yidian.shop.vo.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 省市区获取接口
 *
 * @function:
 * @description: AreaInfoController.java
 * @date: 2021/08/08
 * @author: sunfayun
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/common")
public class AreaInfoController {

    @Resource
    private AreaInfoService areaInfoService;

    /**
     * 获取省市区列表
     *
     * @return 返回层级关系的省市区数据
     */
    @GetMapping("/getAreaInfoList/v1")
    public Result<List<AreaInfoVo>> getAreaInfoList() {
        List<AreaInfoVo> list = areaInfoService.getAreaInfoList();
        if (CollectionUtils.isEmpty(list)) {
            return Result.noData();
        }
        return Result.success(list);
    }

}
