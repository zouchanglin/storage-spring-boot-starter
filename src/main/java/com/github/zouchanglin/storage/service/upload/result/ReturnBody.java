package com.github.zouchanglin.storage.service.upload.result;

/**
 * @author zouchanglin
 */
public interface ReturnBody {

    /**
     * 获取ReturnBody的JSON串(七牛云规定)
     * @return Json https://developer.qiniu.com/kodo/sdk/1239/java#returnbody-uptoken
     */
    String getReturnBodyJson();
}
