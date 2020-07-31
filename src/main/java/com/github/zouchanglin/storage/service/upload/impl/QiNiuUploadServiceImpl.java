package com.github.zouchanglin.storage.service.upload.impl;

import com.github.zouchanglin.storage.config.QiNiuProperties;
import com.github.zouchanglin.storage.service.auth.QiNiuAuthService;
import com.github.zouchanglin.storage.service.upload.QiNiuUploadService;
import com.github.zouchanglin.storage.service.upload.result.DefaultReturnBody;
import com.github.zouchanglin.storage.service.upload.result.ReturnBody;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * @author zouchanglin
 * @date 2020/6/4
 */
public class QiNiuUploadServiceImpl implements QiNiuUploadService {

    @Autowired
    private QiNiuAuthService qiNiuAuthService;
    @Autowired
    private QiNiuProperties qiNiuProperties;
    @Autowired
    private Configuration configuration;
    @Autowired
    private UploadManager uploadManager;

    @Override
    public ReturnBody uploadLocalFile(String filePath, String key, String callBackUrl) {
        try {
            Response response = uploadManager.put(filePath, key, getToken(callBackUrl));
            return new Gson().fromJson(response.bodyString(), DefaultReturnBody.class);
        } catch (QiniuException qiniuException) {
            return null;
        }
    }

    @Override
    public ReturnBody uploadByteArray(byte[] byteArray, String key, String callBackUrl) {
        try {
            Response response = uploadManager.put(byteArray, key, getToken(callBackUrl));
            return new Gson().fromJson(response.bodyString(), DefaultReturnBody.class);
        } catch (QiniuException ex) {
            return null;
        }
    }

    @Override
    public ReturnBody uploadStream(InputStream inputStream, String key, String callBackUrl) {
        try {
            Response response = uploadManager.put(inputStream, key, getToken(callBackUrl), null, null);
            return new Gson().fromJson(response.bodyString(), DefaultReturnBody.class);
        } catch (QiniuException ex) {
            return null;
        }
    }

    @Override
    public ReturnBody breakpointResume(String filePath, String key, String callBackUrl) {
        String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), qiNiuProperties.getBucketName()).toString();
        //设置断点续传文件进度保存目录
        FileRecorder fileRecorder = null;
        try {
            fileRecorder = new FileRecorder(localTempDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadManager uploadManager = new UploadManager(configuration, fileRecorder);
        try {
            Response response = uploadManager.put(filePath, key, getToken(callBackUrl));
            //解析上传成功的结果
            return new Gson().fromJson(response.bodyString(), DefaultReturnBody.class);
        } catch (QiniuException ex) {
            return null;
        }
    }

    private String getToken(String callBackUrl) {
        if (StringUtils.isEmpty(callBackUrl)) {
            return qiNiuAuthService.getToken();
        }else{
            return qiNiuAuthService.getTokenCallBack(callBackUrl);
        }
    }
}
