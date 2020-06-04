package com.github.zouchanglin.storage.service.upload.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author zouchanglin
 * @date 2020/6/4
 */
@Data
public class ReturnBody {

    /**
     * 存储的文件名
     */
    private String key;

    /**
     * 文件Hash
     */
    private String hash;

    /**
     * 存储空间名
     */
    private String bucket;

    /**
     * 原始文件名
     */
    private String fname;

    /**
     * mimeType
     */
    private String mimeType;

    /**
     * UUID
     */
    private String uuid;

    /**
     * 后缀名如.txt
     */
    private String ext;

    /**
     * 前缀名
     */
    private String fprefix;

    /**
     * 文件大小(字节)
     */
    private int fsize;


    public ReturnBody() {
        this.key = "$(key)";
        this.hash = "$(etag)";
        this.bucket = "$(bucket)";
        this.fname = "$(fname)";
        this.mimeType = "$(mimeType)";
        this.uuid = "$(uuid)";
        this.ext = "$(ext)";
        this.fprefix = "$(fprefix)";
        // 转JSON时需要修改 replace("0", "$(fsize)");
        this.fsize = 0;
    }

    /**
     * 获取ReturnBody的JSON串(七牛云规定)
     * @return Json https://developer.qiniu.com/kodo/sdk/1239/java#returnbody-uptoken
     */
    public static String getReturnBodyJson(){
        return JSON.toJSONString(new ReturnBody()).replace("0", "$(fsize)");
    }
}
