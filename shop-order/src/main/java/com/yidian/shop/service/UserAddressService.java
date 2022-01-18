package com.yidian.shop.service;

import com.yidian.shop.entity.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.param.UserAddressParam;
import com.yidian.shop.vo.Result;
import com.yidian.shop.vo.UserAddressVo;

import java.util.List;

/**
 * <p>
 * 收货地址表 服务类
 * </p>
 *
 * @author dev
 * @since 2021-08-26
 */
public interface UserAddressService extends IService<UserAddress> {

    Result<String> configUserAddressInfo(UserAddressParam param);

    Result<String> delUserAddressInfo(Long id);

    Result<UserAddressVo> getUserAddressDetailInfo(Long id);

    Result<List<UserAddressVo>> getUserAddressList();
}
