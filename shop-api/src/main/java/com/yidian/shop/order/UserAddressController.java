package com.yidian.shop.order;


import com.yidian.shop.annotation.AuthorizationAdmin;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.param.UserAddressParam;
import com.yidian.shop.service.UserAddressService;
import com.yidian.shop.vo.Result;
import com.yidian.shop.vo.UserAddressVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 收货地址表接口列表
 *
 * @author dev
 * @since 2021-08-26
 */
@RestController
@RequestMapping("/api/order/mall")
public class UserAddressController {

    @Resource
    private UserAddressService userAddressService;

    /**
     * 添加或者修改用户收货地址
     *
     * @param param 请求参数，更新的时候id必传
     * @return 返回执行结果
     */
    @PostMapping("/configUserAddressInfo/v1")
    public Result<String> configUserAddressInfo(@RequestBody @Valid UserAddressParam param) {
        return userAddressService.configUserAddressInfo(param);
    }

    /**
     * 删除用户收货地址
     *
     * @param id 收货地址ID
     * @return
     */
    @AuthorizationAdmin(userType = CommonConstant.MALL_USER_TYPE)
    @GetMapping("/delUserAddressInfo/{id}")
    public Result<String> delUserAddressInfo(@PathVariable("id") Long id) {
        return userAddressService.delUserAddressInfo(id);
    }

    /**
     * 获取指定收货地址的详细信息
     *
     * @param id 收货地址ID
     * @return 返回收货地址的详细信息
     */
    @GetMapping("/getUserAddressDetailInfo/{id}")
    public Result<UserAddressVo> getUserAddressDetailInfo(@PathVariable("id") Long id) {
        return userAddressService.getUserAddressDetailInfo(id);
    }

    /**
     * 获取用户的地址列表
     *
     * @return 返回用户地址列表
     */
    @GetMapping("/getUserAddressList/v1")
    public Result<List<UserAddressVo>> getUserAddressList() {
        return userAddressService.getUserAddressList();
    }

}

