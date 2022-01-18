package com.yidian.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yidian.shop.component.CurrentRequestComponent;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.entity.UserAddress;
import com.yidian.shop.mapper.UserAddressMapper;
import com.yidian.shop.param.UserAddressParam;
import com.yidian.shop.service.UserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.vo.Result;
import com.yidian.shop.vo.UserAddressVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 收货地址表 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-08-26
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Resource
    private CurrentRequestComponent currentRequestComponent;

    @Override
    public Result<String> configUserAddressInfo(UserAddressParam param) {
        Long userId = currentRequestComponent.getLoginUserId(CommonConstant.MALL_USER_TYPE);
        if (userId == null) {
            return Result.notLoginError();
        }
        if (param.getIsDefault() == 1) {
            this.update(new LambdaUpdateWrapper<UserAddress>().set(UserAddress::getIsDefault, 0).eq(UserAddress::getUserId, userId));
        }
        if (param.getId() != null) {
            UserAddress userAddress = this.getById(param.getId());
            if (userAddress == null) {
                return Result.errorInfo("选择的地址不存在");
            }
            BeanUtils.copyProperties(param, userAddress);
            this.saveOrUpdate(userAddress);
            return Result.dealSuccess();
        }
        // 新增
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(param, userAddress);
        userAddress.setUserId(userId);
        boolean saveResult = this.saveOrUpdate(userAddress);
        if (!saveResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<String> delUserAddressInfo(Long id) {
        boolean delResult = this.removeById(id);
        if (!delResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<UserAddressVo> getUserAddressDetailInfo(Long id) {
        UserAddress userAddress = this.getById(id);
        if (userAddress == null) {
            return Result.noData();
        }
        UserAddressVo userAddressVo = new UserAddressVo();
        BeanUtils.copyProperties(userAddress, userAddressVo);
        return Result.success(userAddressVo);
    }

    @Override
    public Result<List<UserAddressVo>> getUserAddressList() {
        Long userId = currentRequestComponent.getLoginUserId(CommonConstant.MALL_USER_TYPE);
        if (userId == null) {
            return Result.notLoginError();
        }
        List<UserAddress> userAddressList = this.list(new LambdaQueryWrapper<UserAddress>().eq(UserAddress::getUserId, userId));
        if (CollectionUtils.isEmpty(userAddressList)) {
            return Result.noData();
        }
        List<UserAddressVo> list = userAddressList.stream().map(userAddress -> {
            UserAddressVo userAddressVo = new UserAddressVo();
            BeanUtils.copyProperties(userAddress, userAddressVo);
            return userAddressVo;
        }).collect(Collectors.toList());
        return Result.success(list);
    }
}
