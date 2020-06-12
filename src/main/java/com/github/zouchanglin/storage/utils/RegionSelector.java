package com.github.zouchanglin.storage.utils;

import com.github.zouchanglin.storage.config.QiNiuProperties;
import com.qiniu.storage.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 机房选择器
 * @author zouchanglin
 * @date 2020/6/4
 */

@Configuration
public class RegionSelector {
    private final QiNiuProperties qiNiuProperties;

    @Autowired
    public RegionSelector(QiNiuProperties qiNiuProperties) {
        this.qiNiuProperties = qiNiuProperties;
    }

    public Region selectRegion(){
        String region = qiNiuProperties.getRegion();
        switch (region){
            case "huadong":
                return Region.huadong();
            case "huabei":
                return Region.huabei();
            case "huanan":
                return Region.huanan();
            case "xinjiapo":
                return Region.xinjiapo();
            case "beimei":
                return Region.beimei();
            default:
                return Region.autoRegion();
        }
    }
}
