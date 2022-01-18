package com.yidian.shop.common;

import com.yidian.shop.common.param.GetAuthCodeParam;
import com.yidian.shop.common.service.UserAuthCodeService;
import com.yidian.shop.component.OssComponent;
import com.yidian.shop.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户公共接口
 *
 * @function:
 * @description: UserCommonController.java
 * @date: 2021/08/08
 * @author: sunfayun
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/common")
public class UserCommonController {

    @Resource
    private UserAuthCodeService userAuthCodeService;
    @Resource
    private OssComponent ossComponent;

    /**
     * 获取验证码
     *
     * @param param 获取验证码请求参数
     * @return 验证码
     */
    @PostMapping("/getAuthCode/v1")
    public Result<String> getAuthCode(@RequestBody @Valid GetAuthCodeParam param) {
        return Result.success(userAuthCodeService.generateAuthCode(param.getPhone()));
    }

    /**
     * 文件上传
     *
     * @param file 上传的文件内容
     * @return 返回文件路径
     */
    @PostMapping("/uploadImage/v1")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String fileUrl = ossComponent.uploadFile(file);
        if (StringUtils.isBlank(fileUrl)) {
            return Result.dealError();
        }
        return Result.success(fileUrl);
    }

}
