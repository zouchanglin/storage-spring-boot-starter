package com.github.zouchanglin.storage.service.manage;

import com.qiniu.storage.model.FetchRet;
import com.qiniu.storage.model.FileInfo;

import java.util.List;

/**
 * 七牛云单文件操作服务
 * @author zouchanglin
 * @date 2020/6/9
 */
public interface QiNiuManageService {
    /**
     * 获取全部文件信息列表
     * @return 文件信息列表
     */
    List<FileInfo> getAllFileList();

    /**
     * 获取文件信息
     * @param key 文件主键(文件名)
     * @return 文件信息 {@link FileInfo}
     */
    FileInfo getFileInfoByKey(String key);

    /**
     * 修改文件类型
     * @param key 文件主键(文件名)
     * @param newMimeType 文件类型
     * @return  修改文件类型是否成功
     */
    boolean changeMimeType(String key, String newMimeType);

    /**
     * 重命名文件
     * @param key 文件主键(文件名)
     * @param newKey 新文件名
     * @return  重命名是否成功
     */
    boolean renameFile(String key, String newKey);

    /**
     * 复制文件
     * @param key 文件主键(文件名)
     * @param copyKey 复制出的新文件的文件名
     * @return 复制是否成功
     */
    boolean copyFile(String key, String copyKey);

    /**
     * 删除文件
     * @param key 文件主键(文件名)
     * @return 删除是否成功
     */
    boolean deleteFile(String key);

    /**
     * 设置文件过期时间，或者更新已设置了过期时间但尚未被删除的文件的新的过期时间
     * @param days 单位:天
     */
    void deleteAfterDays(String key, int days);

    /**
     * 抓取网络资源到空间
     * @param url 网络资源URL
     * @param key 文件名称
     * @return 抓取结果
     */
    FetchRet pullFileFromNetWork(String url, String key);
}