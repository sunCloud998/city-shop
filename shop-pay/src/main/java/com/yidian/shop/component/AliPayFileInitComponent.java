package com.yidian.shop.component;

import cn.hutool.core.io.FileUtil;
import com.yidian.shop.config.yml.AliPayConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @function:
 * @description: AliPayFileInitComponent.java
 * @date: 2021/09/22
 * @author: sunfayun
 * @version: 1.0
 */
@Slf4j
@Component
public class AliPayFileInitComponent {

    @Resource
    private AliPayConfig aliPayConfig;

    @PostConstruct
    public void initAliPayFile() {
        try {
            File privateKeyFile = new File(aliPayConfig.getPrivateKey());
            FileUtils.copyInputStreamToFile(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(aliPayConfig.getPrivateKey())), privateKeyFile);
            File appCertFile = new File(aliPayConfig.getAppCertPath());
            FileUtils.copyInputStreamToFile(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(aliPayConfig.getAppCertPath())), appCertFile);
            File aliPayCertFile = new File(aliPayConfig.getAliPayCertPath());
            FileUtils.copyInputStreamToFile(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(aliPayConfig.getAliPayCertPath())), aliPayCertFile);
            File aliPayRootCertFile = new File(aliPayConfig.getAliPayRootCertPath());
            FileUtils.copyInputStreamToFile(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(aliPayConfig.getAliPayRootCertPath())), aliPayRootCertFile);
        } catch (Exception e) {
            log.error("alipay file init error, exception:", e);
        }
        log.info("------ alipay file init complete ------");
    }

}
