package com.yidian.shop.merchant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.merchant.entity.MerchantUserInfo;
import com.yidian.shop.merchant.param.MerchantListParam;
import com.yidian.shop.merchant.vo.MerchantUserInfoVo;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * 商家用户表 Mapper 接口
 * </p>
 *
 * @author dev
 * @since 2021-08-04
 */
public interface MerchantUserInfoMapper extends BaseMapper<MerchantUserInfo> {

    IPage<MerchantUserInfo> getMerchantUserList(@Param("page") Page<MerchantUserInfoVo> page,
                                                @Param("param") MerchantListParam param);

}
