package com.github.zouchanglin.storage.service.manage.impl;

import com.github.zouchanglin.storage.config.QiNiuProperties;
import com.github.zouchanglin.storage.service.manage.QiNiuBatchManageService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

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
        int retNumber = 0;
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
}
