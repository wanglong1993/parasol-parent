package org.parasol.column.entity;

import java.io.Serializable;
import java.util.Date;

public class ColumnSelf implements Serializable {
    private Long id;

    private Long parentId;

    private Long sysColId;

    private String columnname;

    private Long userId;

    private Integer orderNum;

    private String pathName;

    private Long subscribeCount;

    private Integer type;

    private Short userOrSystem;

    private Short delStatus;

    private Date createtime;

    private Date updateTime;

    private String columnLevelPath;

    private String tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getSysColId() {
        return sysColId;
    }

    public void setSysColId(Long sysColId) {
        this.sysColId = sysColId;
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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName == null ? null : pathName.trim();
    }

    public Long getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(Long subscribeCount) {
        this.subscribeCount = subscribeCount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Short getUserOrSystem() {
        return userOrSystem;
    }

    public void setUserOrSystem(Short userOrSystem) {
        this.userOrSystem = userOrSystem;
    }

    public Short getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Short delStatus) {
        this.delStatus = delStatus;
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

    public String getColumnLevelPath() {
        return columnLevelPath;
    }

    public void setColumnLevelPath(String columnLevelPath) {
        this.columnLevelPath = columnLevelPath == null ? null : columnLevelPath.trim();
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }
}