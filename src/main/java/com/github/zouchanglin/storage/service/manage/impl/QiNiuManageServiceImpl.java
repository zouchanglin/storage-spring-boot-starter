package com.github.zouchanglin.storage.service.manage.impl;

import com.github.zouchanglin.storage.config.QiNiuProperties;
import com.github.zouchanglin.storage.service.manage.QiNiuManageService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.storage.model.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zouchanglin
 * @date 2020/6/9
 */
@Slf4j
public class QiNiuManageServiceImpl implements QiNiuManageService {
    @Autowired
    private QiNiuProperties qiNiuProperties;

    @Autowired
    private BucketManager bucketManager;

    @Override
    public List<FileInfo> getAllFileList() {
        // 每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        String bucketName = qiNiuProperties.getBucketName();
        BucketManager.FileListIterator listIterator = bucketManager.createFileListIterator(bucketName, "", limit, "");
        List<FileInfo> retList = new ArrayList<>();
        while(listIterator.hasNext()){
            //处理获取的file list结果
            FileInfo[] items = listIterator.next();
            retList.addAll(Arrays.asList(items));
        }
        return retList;
    }

    @Override
    public FileInfo getFileInfoByKey(String key) {
        try {
            return bucketManager.stat(qiNiuProperties.getBucketName(), key);
        } catch (QiniuException qiniuException) {
            qiniuException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean changeMimeType(String key, String newMimeType) {
        //修改文件类型
        try {
            Response response = bucketManager.changeMime(qiNiuProperties.getBucketName(), key, newMimeType);
            log.info("QiNiuManageServiceImpl changeMimeType response {}", response);
            return true;
        } catch (QiniuException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean renameFile(String key, String newKey) {
        try {
            Response response = bucketManager.move(qiNiuProperties.getBucketName(), key, qiNiuProperties.getBucketName(), newKey);
            log.info("QiNiuManageServiceImpl renameFile response {}", response);
            return true;
        } catch (QiniuException qiniuException) {
            qiniuException.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean copyFile(String key, String copyKey) {
        try {
            Response response = bucketManager.copy(qiNiuProperties.getBucketName(), key, qiNiuProperties.getBucketName(), copyKey);
            log.info("QiNiuManageServiceImpl copyFile response {}", response);
            return true;
        } catch (QiniuException qiniuException) {
            qiniuException.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteFile(String key) {
        try {
            Response response = bucketManager.delete(qiNiuProperties.getBucketName(), key);
            log.info("QiNiuManageServiceImpl deleteFile response {}", response);
            return true;
        } catch (QiniuException qiniuException) {
            qiniuException.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteAfterDays(String key, int days) {
        try {
            Response response = bucketManager.deleteAfterDays(qiNiuProperties.getBucketName(), key, days);
            log.info("QiNiuManageServiceImpl deleteAfterDays response {}", response);
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
    }

    @Override
    public FetchRet pullFileFromNetWork(String url, String key) {
        try {
            FetchRet fetchRet = bucketManager.fetch(url, qiNiuProperties.getBucketName(), key);
            log.info("QiNiuManageServiceImpl pullFileFromNetWork fetchRet {}", fetchRet);
            return fetchRet;
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
        return null;
    }
}