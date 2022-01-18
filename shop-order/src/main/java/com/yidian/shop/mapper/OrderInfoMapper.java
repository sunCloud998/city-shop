package com.yidian.shop.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yidian.shop.param.OrderListParam;
import com.yidian.shop.vo.OrderInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author dev
 * @since 2021-08-26
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    Page<OrderInfoVo> getOrderListPage(@Param("page") Page<OrderInfoVo> page,
                                       @Param("param") OrderListParam param);

}
