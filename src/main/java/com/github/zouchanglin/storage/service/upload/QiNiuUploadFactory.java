package com.github.zouchanglin.storage.service.upload;

import com.github.zouchanglin.storage.service.auth.QiNiuAuthService;
import com.github.zouchanglin.storage.utils.RegionSelector;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @author zouchanglin
 * @date 2020/6/4
 */
@org.springframework.context.annotation.Configuration
public class QiNiuUploadFactory {

    private final RegionSelector regionSelector;
    private final QiNiuAuthService qiNiuAuthService;

    @Autowired
    public QiNiuUploadFactory(RegionSelector regionSelector,
                              QiNiuAuthService qiNiuAuthService) {
        this.regionSelector = regionSelector;
        this.qiNiuAuthService = qiNiuAuthService;
    }

    @Bean
    public Configuration configuration(){
        return new Configuration(regionSelector.selectRegion());
    }

    @Bean
    public BucketManager bucketManager(){
        return new BucketManager(qiNiuAuthService.getAuth(), new Configuration(regionSelector.selectRegion()));
    }

    @Bean
    public UploadManager uploadManager(){
        return new UploadManager(new Configuration(regionSelector.selectRegion()));
    }
}