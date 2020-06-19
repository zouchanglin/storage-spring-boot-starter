package com.github.zouchanglin.storage.service.upload;

import com.github.zouchanglin.storage.config.QiNiuProperties;
import com.github.zouchanglin.storage.service.auth.QiNiuAuthService;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @author zouchanglin
 * @date 2020/6/19
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(QiNiuProperties.class)
public class BucketManagerFactory {
    @Resource
    private QiNiuAuthService qiNiuAuthService;

    @Resource
    private Configuration configuration;

    @Bean
    public BucketManager bucketManager(){
        return new BucketManager(qiNiuAuthService.getAuth(), configuration);
    }
}
