package com.ginkgocap.parasol.knowledge.model;

/**
 * Created by Chen peifeng on 2016/3/16.
 */
public class LoginInfo {

    public LoginInfo(Long loginAppId, Long loginUserId) {
        this.appId = loginAppId;
        this.userId = loginUserId;
    }

    Long appId;
    Long userId;

    public Long getAppId() {
        return appId;
    }

    public Long getUserId() {
        return userId;
    }
}
