package com.ginkgocap.parasol.user.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by xutlong on 2016/5/24.
 * 用户黑名单表
 */
@Entity
@Table(name = "tb_user_blacklist", catalog = "parasol_user")
public class UserBlackList {
    private Long id;
    private Long userId;
    private Long blackUserId;
    private Long appId;
    private Long ctime;
    private Long utime;
    private String ip;

    @Id
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "blackUserId")
    public Long getBlackUserId() {
        return blackUserId;
    }

    public void setBlackUserId(Long blackUserId) {
        this.blackUserId = blackUserId;
    }

    @Column(name = "appId")
    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    @Column(name = "ctime")
    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    @Column(name = "utime")
    public Long getUtime() {
        return utime;
    }

    public void setUtime(Long utime) {
        this.utime = utime;
    }

    @Column(name = "ip", length = 50)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
