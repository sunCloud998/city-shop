package com.yidian.shop.common.service;

import com.yidian.shop.common.entity.AreaInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.common.vo.AreaInfoVo;

import java.util.List;

/**
 * <p>
 * 省市区字典表 服务类
 * </p>
 *
 * @author dev
 * @since 2021-08-08
 */
public interface AreaInfoService extends IService<AreaInfo> {

    List<AreaInfoVo> getAreaInfoList();

}
