package com.yidian.shop.component;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @function:
 * @description: OssComponent.java
 * @date: 2021/08/23
 * @author: sunfayun
 * @version: 1.0
 */
@Component
@Slf4j
public class OssComponent {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Value("${aliyun.oss.fileDir}")
    private String fileDir;

    /**
     * 1、单个文件上传
     *
     * @param file
     * @return 返回完整URL地址
     */
    public String uploadFile(MultipartFile file) {
        String fileUrl = uploadImg2Oss(file);
        String str = getFileUrl(fileUrl);
        return str.trim();
    }

    /**
     * 1、单个文件上传(指定文件名（带后缀）)
     *
     * @param file
     * @return 返回完整URL地址
     */
    public String uploadFile(MultipartFile file, String fileName) {
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, fileName);
            return fileName;
        } catch (Exception e) {
            return "上传失败";
        }
    }

    /**
     * 2、多文件上传
     *
     * @param fileList
     * @return 返回完整URL，逗号分隔
     */
    public String uploadFile(List<MultipartFile> fileList) {
        String fileUrl = "";
        String str = "";
        String photoUrl = "";
        for (int i = 0; i < fileList.size(); i++) {
            fileUrl = uploadImg2Oss(fileList.get(i));
            str = getFileUrl(fileUrl);
            if (i == 0) {
                photoUrl = str;
            } else {
                photoUrl += "," + str;
            }
        }
        return photoUrl.trim();
    }

    /**
     * 3、通过文件名获取文完整件路径
     *
     * @param fileUrl
     * @return 完整URL路径
     */
    public String getFileUrl(String fileUrl) {
        if (fileUrl != null && fileUrl.length() > 0) {
            String[] split = fileUrl.split("/");
            String url = this.getUrl(this.fileDir + split[split.length - 1]);
            return url;
        }
        return null;
    }

    //获取去掉参数的完整路径
    private String getShortUrl(String url) {
        String[] imgUrls = url.split("\\?");
        return imgUrls[0].trim();
    }

    // 获得url链接
    private String getUrl(String key) {
        // 设置URL过期时间为20年  3600l* 1000*24*365*20
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 20);
        // 生成URL
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return getShortUrl(url.toString());
        }
        return null;
    }

    // 上传文件
    private String uploadImg2Oss(MultipartFile file) {
        //1、限制最大文件为20M
        if (file.getSize() > 1024 * 1024 * 20) {
            return "图片太大";
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase(); //文件后缀
        String uuid = UUID.randomUUID().toString();
        String name = uuid + suffix;

        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            return "上传失败";
        }
    }


    // 上传文件（指定文件名）
    private String uploadFile2OSS(InputStream inStream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult putResult = ossClient.putObject(bucketName, fileDir + fileName, inStream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error("文件上传发生异常，exception:", e);
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                log.error("IOException:", e);
            }
        }
        return ret;
    }

    private static String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        //PDF
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        return "image/jpg";
    }

}
