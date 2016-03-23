package com.ginkgocap.parasol.knowledge.model;

/**
 * Created by Chen peifeng on 2016/3/16.
 */
public class LoginInfo {

    public LoginInfo(Long loginAppId, Long loginUserId) {
        this.loginAppId = loginAppId;
        this.loginUserId = loginUserId;
    }

    Long loginAppId;
    Long loginUserId;

    public Long getLoginAppId() {
        return loginAppId;
    }

    public Long getLoginUserId() {
        return loginUserId;
    }
}
