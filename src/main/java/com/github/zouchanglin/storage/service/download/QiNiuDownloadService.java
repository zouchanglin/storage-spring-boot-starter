package com.github.zouchanglin.storage.service.download;

import java.io.File;

/**
 * @author zouchanglin
 * @date 2020/6/9
 */
public interface QiNiuDownloadService {
    /**
     * 下载公有仓库的文件到本地
     * @param localPath 本地路径
     * @param fileName 文件名称(本地文件名称和云端文件名称一致)
     * @param coverage 是否强制覆盖
     * @return 下载后的File
     */
    File publicLocalDownload(String localPath, String fileName, boolean coverage);

    /**
     * 获取公有仓库的文件下载URL
     * @param fileName 文件名称
     * @return download url
     */
    String publicDownloadUrl(String fileName);


    /**
     * 下载私有仓库的文件到本地
     * @param localPath 本地路径
     * @param fileName 文件名称(本地文件名称和云端文件名称一致)
     * @param coverage 是否强制覆盖
     * @return 下载后的File
     */
    File privateLocalDownload(String localPath, String fileName, boolean coverage);

    /**
     * 获取公有仓库的文件下载URL(有效期30分钟)
     * @param fileName 文件名称
     * @return download url
     */
    String privateDownloadUrl(String fileName);
}
