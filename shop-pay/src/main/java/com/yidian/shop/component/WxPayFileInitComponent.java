package com.yidian.shop.component;

import com.yidian.shop.config.yml.AliPayConfig;
import com.yidian.shop.config.yml.WxPayConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
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
public class WxPayFileInitComponent {

    @Resource
    private WxPayConfig wxPayConfig;

    @PostConstruct
    public void initWxPayFile() {
        try {
            File certPathFile = new File(wxPayConfig.getCertPath());
            FileUtils.copyInputStreamToFile(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(wxPayConfig.getCertPath())), certPathFile);
        } catch (Exception e) {
            log.error("wxPay file init error, exception:", e);
        }
        log.info("------ wxPay file init complete ------");
    }

}
