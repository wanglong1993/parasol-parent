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
    private Long fileId;
    // 是否目录（0：不是，1：是）
    private int isDir;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getServerFilename() {
        return serverFilename;
    }

    public void setServerFilename(String serverFilename) {
        this.serverFilename = serverFilename;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public int getIsDir() {
        return isDir;
    }

    public void setIsDir(int isDir) {
        this.isDir = isDir;
    }
}
