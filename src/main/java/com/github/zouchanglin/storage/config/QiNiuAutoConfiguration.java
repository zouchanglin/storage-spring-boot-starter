package com.github.zouchanglin.storage.config;

import com.github.zouchanglin.storage.service.auth.QiNiuAuthService;
import com.github.zouchanglin.storage.service.auth.impl.QiNiuAuthServiceImpl;
import com.github.zouchanglin.storage.service.download.QiNiuDownloadService;
import com.github.zouchanglin.storage.service.download.impl.QiNiuDownloadServiceImpl;
import com.github.zouchanglin.storage.service.manage.QiNiuBatchManageService;
import com.github.zouchanglin.storage.service.manage.QiNiuManageService;
import com.github.zouchanglin.storage.service.manage.impl.QiNiuBatchManageServiceImpl;
import com.github.zouchanglin.storage.service.manage.impl.QiNiuManageServiceImpl;
import com.github.zouchanglin.storage.service.upload.QiNiuUploadService;
import com.github.zouchanglin.storage.service.upload.impl.QiNiuUploadServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zouchanglin
 * @date 2020/6/4
 */
@Configuration
public class QiNiuAutoConfiguration {

    @Bean
    QiNiuAuthService qiNiuAuthService() {
        return new QiNiuAuthServiceImpl();
    }

    @Bean
    QiNiuDownloadService qiNiuDownloadService() {
        return new QiNiuDownloadServiceImpl();
    }

    @Bean
    QiNiuManageService qiNiuManageService(){
        return new QiNiuManageServiceImpl();
    }

    @Bean
    QiNiuBatchManageService qiNiuBatchManageService(){
        return new QiNiuBatchManageServiceImpl();
    }

    @Bean
    QiNiuUploadService qiNiuUploadService() {
        return new QiNiuUploadServiceImpl();
    }
}
