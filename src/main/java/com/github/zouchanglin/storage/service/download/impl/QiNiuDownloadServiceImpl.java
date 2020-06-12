package com.github.zouchanglin.storage.service.download.impl;

import com.github.zouchanglin.storage.config.QiNiuProperties;
import com.github.zouchanglin.storage.service.auth.QiNiuAuthService;
import com.github.zouchanglin.storage.service.download.QiNiuDownloadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * @author zouchanglin
 * @date 2020/6/9
 */
@Slf4j
public class QiNiuDownloadServiceImpl implements QiNiuDownloadService {
    @Autowired
    private QiNiuProperties qiNiuProperties;

    @Autowired
    private QiNiuAuthService qiNiuAuthService;

    @Override
    public File publicLocalDownload(String localPath, String fileName, boolean coverage) {
        String url = String.format("http://%s/%s", qiNiuProperties.getDomainName(), fileName);
        try {
            File file = new File(localPath + File.pathSeparator + fileName);
            // 文件存在且不可覆盖
            if(file.exists() && !coverage) return file;

            boolean deleteResult = file.delete();
            log.info("delete exists file result is {}", deleteResult);
            file = FileUtils.toFile(new URL(url));
            return file;
        } catch (MalformedURLException e) {
            log.error("network error, download file failed.");
            return null;
        }
    }

    @Override
    public String publicDownloadUrl(String fileName) {
        return String.format("http://%s/%s", qiNiuProperties.getDomainName(), fileName);
    }

    @Override
    public File privateLocalDownload(String localPath, String fileName, boolean coverage) {
        String url = String.format("http://%s/%s", qiNiuProperties.getDomainName(), fileName);
        File file = new File(localPath + File.pathSeparator + fileName);
        // 文件存在且不可覆盖
        if(file.exists() && !coverage) return file;
        // 定义半小时的链接过期时间
        long expireInSeconds = 1800;
        try {
            String encodingUrl = URLEncoder.encode(url, "UTF-8").replace("+", "%20");
            String finalUrl = qiNiuAuthService.getAuth().privateDownloadUrl(encodingUrl, expireInSeconds);
            file = FileUtils.toFile(new URL(finalUrl));
            return file;
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            log.error("network error, download file failed.");
            return null;
        }
    }

    @Override
    public String privateDownloadUrl(String fileName) {
        String url = String.format("http://%s/%s", qiNiuProperties.getDomainName(), fileName);
        // 定义半小时的链接过期时间
        long expireInSeconds = 1800;
        try {
            String encodingUrl = URLEncoder.encode(url, "UTF-8").replace("+", "%20");
            return qiNiuAuthService.getAuth().privateDownloadUrl(encodingUrl, expireInSeconds);
        } catch (UnsupportedEncodingException e) {
            log.error("network error, get download file url failed.");
            return null;
        }
    }
}
