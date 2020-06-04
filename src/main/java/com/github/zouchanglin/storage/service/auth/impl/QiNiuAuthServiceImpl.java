package com.github.zouchanglin.storage.service.auth.impl;

import com.github.zouchanglin.storage.config.QiNiuProperties;
import com.github.zouchanglin.storage.service.auth.QiNiuAuthService;
import com.github.zouchanglin.storage.service.upload.result.ReturnBody;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author zouchanglin
 * @date 2020/6/4
 */
@Configuration
public class QiNiuAuthServiceImpl implements QiNiuAuthService {
    private final QiNiuProperties qiNiuProperties;

    @Autowired
    public QiNiuAuthServiceImpl(QiNiuProperties qiNiuProperties) {
        this.qiNiuProperties = qiNiuProperties;
    }

    @Override
    public String getToken() {
        Auth auth = Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey());
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", ReturnBody.getReturnBodyJson());
        long expireSeconds = 3600;
        return auth.uploadToken(qiNiuProperties.getBucketName(), null, expireSeconds, putPolicy);
    }

    @Override
    public String getTokenByCover(String fileName) {
        Auth auth = Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey());
        return auth.uploadToken(qiNiuProperties.getBucketName(), fileName);
    }

    @Override
    public String getTokenCallBack(String callBackUrl) {
        Auth auth = Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey());
        StringMap putPolicy = new StringMap();
        putPolicy.put("callbackUrl", callBackUrl);
        putPolicy.put("callbackBody", ReturnBody.getReturnBodyJson());
        putPolicy.put("callbackBodyType", "application/json");
        long expireSeconds = 3600;
        return auth.uploadToken(qiNiuProperties.getBucketName(), null, expireSeconds, putPolicy);
    }
}
