package com.ginkgocap.parasol.knowledge.model;

import java.io.Serializable;

/**
 * Created by Chen Peifeng on 2016/3/16.
 */
public class ReceiversInfo implements Serializable {
    private static final long serialVersionUID = 4341300097772762608L;
    private long userId;//用户id
    private int userStatus;//阅读状态
    private int type;//0 通过权限设置而来，1通过@而来
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public int getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
}