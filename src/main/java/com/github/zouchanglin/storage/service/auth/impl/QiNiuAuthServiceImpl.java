package com.github.zouchanglin.storage.service.auth.impl;

import com.github.zouchanglin.storage.config.QiNiuProperties;
import com.github.zouchanglin.storage.service.auth.QiNiuAuthService;
import com.github.zouchanglin.storage.service.upload.result.DefaultReturnBody;
import com.github.zouchanglin.storage.service.upload.result.ReturnBody;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zouchanglin
 * @date 2020/6/4
 */
public class QiNiuAuthServiceImpl implements QiNiuAuthService {
    @Autowired
    private QiNiuProperties qiNiuProperties;

    @Override
    public String getToken() {
        Auth auth = Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey());
        StringMap putPolicy = new StringMap();

        putPolicy.put("returnBody", new DefaultReturnBody().getReturnBodyJson());
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
        putPolicy.put("callbackBody", new DefaultReturnBody().getReturnBodyJson());
        putPolicy.put("callbackBodyType", "application/json");
        long expireSeconds = 3600;
        return auth.uploadToken(qiNiuProperties.getBucketName(), null, expireSeconds, putPolicy);
    }

    @Override
    public Auth getAuth() {
        return Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey());
    }



    //-----------------------------------------


    @Override
    public String getToken(ReturnBody returnBody) {
        Auth auth = Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey());
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", returnBody.getReturnBodyJson());
        long expireSeconds = 3600;
        return auth.uploadToken(qiNiuProperties.getBucketName(), null, expireSeconds, putPolicy);
    }

    @Override
    public String getTokenCallBack(String callBackUrl, ReturnBody returnBody) {
        Auth auth = Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey());
        StringMap putPolicy = new StringMap();
        putPolicy.put("callbackUrl", callBackUrl);
        putPolicy.put("callbackBody", returnBody.getReturnBodyJson());
        putPolicy.put("callbackBodyType", "application/json");
        long expireSeconds = 3600;
        return auth.uploadToken(qiNiuProperties.getBucketName(), null, expireSeconds, putPolicy);
    }
}
