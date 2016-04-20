package org.parasol.column.entity;

import java.io.Serializable;
import java.util.Date;

public class ColumnSys implements Serializable {
    private Long id;

    private String columnname;

    private Long userId;

    private Long parentId;

    private Date createtime;

    private String pathName;

    private String columnLevelPath;

    private Byte delStatus;

    private Date updateTime;

    private Long subscribeCount;

    private Short type;

    private Short userOrSystem;

    private Short orderNum;

    private String tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname == null ? null : columnname.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName == null ? null : pathName.trim();
    }

    public String getColumnLevelPath() {
        return columnLevelPath;
    }

    public void setColumnLevelPath(String columnLevelPath) {
        this.columnLevelPath = columnLevelPath == null ? null : columnLevelPath.trim();
    }

    public Byte getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Byte delStatus) {
        this.delStatus = delStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(Long subscribeCount) {
        this.subscribeCount = subscribeCount;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getUserOrSystem() {
        return userOrSystem;
    }

    public void setUserOrSystem(Short userOrSystem) {
        this.userOrSystem = userOrSystem;
    }

    public Short getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Short orderNum) {
        this.orderNum = orderNum;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }
}