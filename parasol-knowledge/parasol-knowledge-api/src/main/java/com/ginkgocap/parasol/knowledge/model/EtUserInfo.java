package com.ginkgocap.parasol.knowledge.model;

/**
 * Created by Chen Peifeng on 2016/3/16.
 */
public class EtUserInfo {
    private long userOneId;//发起者
    private String userOneName;
    private long userTwoId;//被”@“的人
    private String userTwoName;
    public long getUserOneId() {
        return userOneId;
    }
    public void setUserOneId(long userOneId) {
        this.userOneId = userOneId;
    }
    public String getUserOneName() {
        return userOneName;
    }
    public void setUserOneName(String userOneName) {
        this.userOneName = userOneName;
    }
    public long getUserTwoId() {
        return userTwoId;
    }
    public void setUserTwoId(long userTwoId) {
        this.userTwoId = userTwoId;
    }
    public String getUserTwoName() {
        return userTwoName;
    }
    public void setUserTwoName(String userTwoName) {
        this.userTwoName = userTwoName;
    }

}