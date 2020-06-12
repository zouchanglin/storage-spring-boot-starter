package com.github.zouchanglin.storage.config;

import com.github.zouchanglin.storage.service.auth.QiNiuAuthService;
import com.github.zouchanglin.storage.service.auth.impl.QiNiuAuthServiceImpl;
import com.github.zouchanglin.storage.service.download.QiNiuDownloadService;
import com.github.zouchanglin.storage.service.download.impl.QiNiuDownloadServiceImpl;
import com.github.zouchanglin.storage.service.manage.QiNiuManageService;
import com.github.zouchanglin.storage.service.manage.impl.QiNiuManageServiceImpl;
import com.github.zouchanglin.storage.service.upload.QiNiuUploadService;
import com.github.zouchanglin.storage.service.upload.impl.QiNiuUploadServiceImpl;
import com.qiniu.storage.UploadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zouchanglin
 * @date 2020/6/4
 */
@Configuration
@EnableConfigurationProperties(QiNiuProperties.class)
public class QiNiuAutoConfiguration {
    private final QiNiuProperties qiNiuProperties;

    private final QiNiuAuthService qiNiuAuthService;

    private final com.qiniu.storage.Configuration configuration;

    private final UploadManager uploadManager;

    @Autowired
    public QiNiuAutoConfiguration(QiNiuProperties qiNiuProperties, QiNiuAuthService qiNiuAuthService,
                                  com.qiniu.storage.Configuration configuration, UploadManager uploadManager) {
        this.qiNiuProperties = qiNiuProperties;
        this.qiNiuAuthService = qiNiuAuthService;
        this.configuration = configuration;
        this.uploadManager = uploadManager;
    }

    @Bean
    QiNiuAuthService qiNiuAuthService() {
        return new QiNiuAuthServiceImpl(qiNiuProperties);
    }

    @Bean
    QiNiuUploadService qiNiuUploadService() {
        return new QiNiuUploadServiceImpl(qiNiuAuthService, qiNiuProperties, configuration, uploadManager);
    }

    @Bean
    QiNiuDownloadService qiNiuDownloadService() {
        return new QiNiuDownloadServiceImpl();
    }

    @Bean
    QiNiuManageService qiNiuManageService(){
        return new QiNiuManageServiceImpl();
    }
}