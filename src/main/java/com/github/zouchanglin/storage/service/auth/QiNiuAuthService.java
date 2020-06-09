package com.github.zouchanglin.storage.service.auth;

import com.qiniu.util.Auth;

/**
 * @author zouchanglin
 * @date 2020/6/4
 */
public interface QiNiuAuthService {

    /**
     * 客户端获取凭证 https://developer.qiniu.com/kodo/sdk/javascript
     * @return token
     */
    String getToken();

    /**
     * 客户端获取覆盖上传(上传文件覆盖)的凭证
     * @param fileName 需要覆盖的文件名
     * @return token
     */
    String getTokenByCover(String fileName);


    /**
     * 客户端获取携带回调接口凭证
     * @param callBackUrl 回调URL地址,如 http://example.com/callback/
     * @return token
     */
    String getTokenCallBack(String callBackUrl);

    /**
     * 获取Auth验证对象
     * @return {@link Auth}
     */
    Auth getAuth();
}