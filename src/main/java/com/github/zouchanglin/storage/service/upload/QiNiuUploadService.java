package com.github.zouchanglin.storage.service.upload;

import com.github.zouchanglin.storage.service.upload.result.ReturnBody;

import java.io.InputStream;

/**
 * @author zouchanglin
 * @date 2020/6/4
 */
public interface QiNiuUploadService {
    /**
     * 上传本地文件
     * @param filePath 本地文件路径
     * @param key 上传后目标文件名
     * @param callBackUrl 回调地址
     * @return 上传结果
     */
    ReturnBody uploadLocalFile(String filePath, String key, String callBackUrl);


    /**
     * 上传字节数组
     * @param byteArray 字节数组
     * @param key 上传后目标文件名
     * @param callBackUrl 回调地址
     * @return 默认上传结果
     */
    ReturnBody uploadByteArray(byte[] byteArray, String key, String callBackUrl);

    /**
     * 上传流
     * @param inputStream 输入流
     * @param key 上传后目标文件名
     * @param callBackUrl 回调地址
     * @return 默认上传结果
     */
    ReturnBody uploadStream(InputStream inputStream, String key, String callBackUrl);


    /**
     * 本地文件断点续传
     * @param filePath 本地文件路径
     * @param key 上传后目标文件名
     * @param callBackUrl 回调地址
     * @return 默认上传结果
     */
    ReturnBody breakpointResume(String filePath, String key, String callBackUrl);
}