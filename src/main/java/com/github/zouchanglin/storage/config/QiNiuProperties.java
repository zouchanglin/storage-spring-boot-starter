package com.github.zouchanglin.storage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zouchanglin
 * @date 2020/6/4
 */
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuProperties {
    /**
     * AK
     */
    private String accessKey = "";

    /**
     * SK
     */
    private String secretKey = "";

    /**
     * bucket
     */
    private String bucketName = "";

    /**
     * 机房 huadong、huabei、huanan、beimei、xinjiapo
     */
    private String region;

    /**
     * 仓库的域名
     */
    private String domainName;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
