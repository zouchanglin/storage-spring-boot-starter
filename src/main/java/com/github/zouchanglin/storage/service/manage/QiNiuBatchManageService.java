package com.github.zouchanglin.storage.service.manage;

import java.util.Map;

/**
 * 七牛云文件批量操作服务
 * @author zouchanglin
 * @date 2020/6/11
 */
public interface QiNiuBatchManageService {
    /**
     * 批量修改文件类型
     * @param keyMimeMap key为文件名(主键)、value为新类型
     * @return 成功修改数量
     */
    int batchChangeMimeType(Map<String, String> keyMimeMap);
}