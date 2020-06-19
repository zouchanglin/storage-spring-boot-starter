package com.github.zouchanglin.storage.service.upload;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @author zouchanglin
 * @date 2020/6/4
 */
@org.springframework.context.annotation.Configuration
public class UploadManagerFactory {

    @Resource
    private Configuration configuration;

    @Bean
    public UploadManager uploadManager(){
        return new UploadManager(configuration);
    }
}
