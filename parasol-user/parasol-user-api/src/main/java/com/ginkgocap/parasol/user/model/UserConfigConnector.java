package com.ginkgocap.parasol.user.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by xutlong on 2016/5/30.
 * 用户个人主页可见性权限，保存可见好友
 */
@Entity
@Table(name = "tb_user_config_connector", catalog = "parasol_user")
public class UserConfigConnector implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long Id;
    private Long userId;
    private Long appId;
    private Long friendId;
    private int type;
    private Long ctime;
    private Long utime;
    private String ip;

    public UserConfigConnector() {
        super();
    }

    public UserConfigConnector(Long id, Long appId, Long userId, Long ctime, Long friendId, Long utime, String ip) {
        Id = id;
        this.appId = appId;
        this.userId = userId;
        this.ctime = ctime;
        this.friendId = friendId;
        this.utime = utime;
        this.ip = ip;
    }

    @Id
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator")
    @Column(name = "id")
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "appId")
    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    @Column(name = "friendId")
    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
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

    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name ="type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
