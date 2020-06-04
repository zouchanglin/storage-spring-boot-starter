package com.github.zouchanglin.storage.service.upload;

import com.github.zouchanglin.storage.utils.RegionSelector;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @author zouchanglin
 * @date 2020/6/4
 */
@org.springframework.context.annotation.Configuration
public class UploadFactory {
    private final RegionSelector regionSelector;

    @Autowired
    public UploadFactory(RegionSelector regionSelector) {
        this.regionSelector = regionSelector;
    }

    @Bean
    public Configuration configuration(){
        return new Configuration(regionSelector.selectRegion());
    }

    @Bean
    public UploadManager uploadManager(){
        return new UploadManager(configuration());
    }
}
