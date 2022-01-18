package com.yidian.shop.bo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @function:
 * @description: H5SceneInfo.java
 * @date: 2021/10/11
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class H5SceneInfo {

    private H5 h5Info;

    @Data
    public static class H5 {
        private String type;
        private String app_name;
        private String bundle_id;
        private String package_name;
        private String wap_url;
        private String wap_name;
    }

}
