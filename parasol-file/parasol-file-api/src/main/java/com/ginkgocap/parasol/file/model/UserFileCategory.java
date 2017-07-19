package com.ginkgocap.parasol.file.model;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by xutlong on 2017/7/7.
 * 用户目录文件组合结构 最新表结构
 */
public class UserFileCategory implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;
    // 用户ID
    private Long userId;
    // 目录名称
    private String serverFilename;
    // 目录排序标识
    private String sortId;
    // 创建时间
    private Date ctime;
    // 父目录ID
    private Long parentId;
    // 文件ID
    private Long fielId;
    // 是否目录（0：不是，1：是）
    private int isDir;

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

    @Column(name = "category_name")
    public String getServerFilename() {
        return serverFilename;
    }

    public void setServerFilename(String serverFilename) {
        this.serverFilename = serverFilename;
    }

    @Column(name = "sort_id")
    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    @Column(name = "ctime")
    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "file_id")
    public Long getFielId() {
        return fielId;
    }

    public void setFielId(Long fielId) {
        this.fielId = fielId;
    }

    @Column(name = "is_dir")
    public int getIsDir() {
        return isDir;
    }

    public void setIsDir(int isDir) {
        this.isDir = isDir;
    }
}
