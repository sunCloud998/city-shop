package com.yidian.shop.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.entity.RefundInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yidian.shop.param.RefundListParam;
import com.yidian.shop.vo.RefundInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单退货申请 Mapper 接口
 * </p>
 *
 * @author dev
 * @since 2021-08-26
 */
public interface RefundInfoMapper extends BaseMapper<RefundInfo> {

    Page<RefundInfoVo> getRefundList(@Param("page") Page<RefundInfoVo> page,
                                     @Param("param") RefundListParam param);
}
