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

    /**
     * 批量删除文件
     * @param keys key为文件名(主键)，keys即主键数组
     * @return 成功删除文件数量
     */
    int batchDeleteFile(String[] keys);

    /**
     * 批量重命名文件
     * @param nameMap key为文件名(主键)、value为新Key
     * @return 成功重命名文件数量
     */
    int batchRenameFile(Map<String, String> nameMap);

    /**
     * 批量复制文件
     * @param keys 文件名(主键)
     * @param suffix 复制出的文件名的后缀,如_copy
     * @return 成功文件数量
     */
    int batchCopyFile(String[] keys, String suffix);
}