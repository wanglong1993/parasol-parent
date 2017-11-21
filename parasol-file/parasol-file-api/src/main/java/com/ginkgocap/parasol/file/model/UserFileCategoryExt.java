package com.ginkgocap.parasol.file.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xutlong on 2017/7/7.
 */
public class UserFileCategoryExt implements Serializable{

    private Long id;
    // 文件ID
    private Long fileId;
    // 用户ID
    private Long userId;
    // 是否目录（0：不是，1：是）
    private int isDir;
    // 文件名称
    private String serverFilename;
    // 目录排序标识
    private String sortId = "";
    // 创建时间
    private Date ctime;
    // 父目录ID
    private Long parentId;
    // url
    private String url = "";
    // 文件类型
    private int fileType = 0;
    // 文件大小
    private long fileSize = 0;
    private String filePath;
    private String serverHost;
    // 文件缩略图
    private String thumbnailsPath = "";
    // 备注信息 (如果视频存时长)
    private String remark = "";
    // 文件名称
    private String fileTitle = "";
    // 文件类型
    private int ModuleType = 0;

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIsDir() {
        return isDir;
    }

    public void setIsDir(int isDir) {
        this.isDir = isDir;
    }

    public String getServerFilename() {
        return serverFilename;
    }

    public void setServerFilename(String server_filename) {
        this.serverFilename = server_filename;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getThumbnailsPath() {
        return thumbnailsPath;
    }

    public void setThumbnailsPath(String thumbnailsPath) {
        this.thumbnailsPath = thumbnailsPath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public int getModuleType() {
        return ModuleType;
    }

    public void setModuleType(int moduleType) {
        ModuleType = moduleType;
    }
}
