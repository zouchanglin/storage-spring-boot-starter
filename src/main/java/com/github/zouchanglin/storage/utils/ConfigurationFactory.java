package com.github.zouchanglin.storage.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author zouchanglin
 * @date 2020/6/19
 */
@Configuration
public class ConfigurationFactory {
    @Resource
    private RegionSelector regionSelector;

    @Bean
    public com.qiniu.storage.Configuration configuration(){
        return new com.qiniu.storage.Configuration(regionSelector.selectRegion());
    }
}
