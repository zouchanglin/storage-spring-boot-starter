package com.github.zouchanglin.storage.config;



import com.github.zouchanglin.storage.service.auth.QiNiuAuthService;
import com.github.zouchanglin.storage.service.auth.impl.QiNiuAuthServiceImpl;
import com.github.zouchanglin.storage.service.upload.QiNiuUploadService;
import com.github.zouchanglin.storage.service.upload.impl.QiNiuUploadServiceImpl;
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

    public QiNiuAutoConfiguration(QiNiuProperties qiNiuProperties) {
        this.qiNiuProperties = qiNiuProperties;
    }

    @Bean
    QiNiuAuthService qiNiuAuthService() {
        return new QiNiuAuthServiceImpl(qiNiuProperties);
    }

    @Bean
    QiNiuUploadService qiNiuUploadService() {
        return new QiNiuUploadServiceImpl();
    }
}