package com.yidian.shop.common.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 获取验证码请求参数
 *
 * @function:
 * @description: GetAuthCodeParam.java
 * @date: 2021/07/30
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class GetAuthCodeParam {

    /**
     * 电话号码
     */
    @NotBlank(message = "电话号码不能为空")
    private String phone;

}
