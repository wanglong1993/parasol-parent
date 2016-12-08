package org.parasol.column.entity;

import java.io.Serializable;
import java.util.Date;

public class NewColumnCustom implements Serializable {

    private static final long serialVersionUID = 13253426246L;

    private Long sid;

    private Long userId;

    private Short userOrSystem;

    private Date createtime;

    private Date updateTime;

    public NewColumnCustom(Long sid, Long userId, Short userOrSystem, Date createtime, Date updateTime) {
        this.sid = sid;
        this.userId = userId;
        this.userOrSystem = userOrSystem;
        this.createtime = createtime;
        this.updateTime = updateTime;
    }

    public NewColumnCustom() {
        super();
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getUserOrSystem() {
        return userOrSystem;
    }

    public void setUserOrSystem(Short userOrSystem) {
        this.userOrSystem = userOrSystem;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}