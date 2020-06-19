package com.github.zouchanglin.storage.service.manage.impl;

import com.github.zouchanglin.storage.config.QiNiuProperties;
import com.github.zouchanglin.storage.service.manage.QiNiuBatchManageService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

/**
 * @author zouchanglin
 * @date 2020/6/11
 */
public class QiNiuBatchManageServiceImpl implements QiNiuBatchManageService {
    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private QiNiuProperties qiNiuProperties;

    @Override
    public int batchChangeMimeType(Map<String, String> keyMimeMap) {
        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        //添加指令
        for (Map.Entry<String, String> entry : keyMimeMap.entrySet()) {
            String key = entry.getKey();
            String newMimeType = entry.getValue();
            batchOperations.addChgmOp(qiNiuProperties.getBucketName(), key, newMimeType);
        }
        return countChangeItem(batchOperations);
    }

    private int countChangeItem(BucketManager.BatchOperations batchOperations) {
        int retNumber = 0;
        return countItemByOpt(batchOperations, retNumber);
    }

    private int countItemByOpt(BucketManager.BatchOperations batchOperations, int retNumber) {
        try {
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for(BatchStatus batchStatus: batchStatusList){
                if(200 == batchStatus.code) retNumber++;
            }
        } catch (QiniuException qiniuException) {
            qiniuException.printStackTrace();
        }
        return retNumber;
    }

    @Override
    public int batchDeleteFile(String[] keys) {
        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        batchOperations.addDeleteOp(qiNiuProperties.getBucketName(), keys);
        return countChangeItem(batchOperations);
    }

    @Override
    public int batchRenameFile(Map<String, String> nameMap) {
        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        Set<String> keySet = nameMap.keySet();
        int retNumber = 0;
        for (String key : keySet) {
            batchOperations.addMoveOp(qiNiuProperties.getBucketName(), key, qiNiuProperties.getBucketName(), nameMap.get(key));
        }
        return countItemByOpt(batchOperations, retNumber);
    }

    @Override
    public int batchCopyFile(String[] keys, String suffix) {
        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        for (String key : keys) {
            batchOperations.addCopyOp(qiNiuProperties.getBucketName(), key, qiNiuProperties.getBucketName(), key + suffix);
        }
        int retNumber = 0;
        Response response;
        try {
            response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for(BatchStatus batchStatus : batchStatusList){
                if(200 == batchStatus.code) retNumber++;
            }
        } catch (QiniuException qiniuException) {
            qiniuException.printStackTrace();
        }
        return retNumber;
    }
}
