package com.ginkgocap.parasol.file.model;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@JsonFilter("com.ginkgocap.parasol.file.model.FileCategory")
@Entity
@Table(name = "tb_file_category")
public class FileCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    // 文件索引
    private String fileId;
    // 文件名称
    private String fileName;
    // 目录索引
    private Long categoryId;
    // 用户ID
    private Long userId;
    // 创建时间
    private Date ctime;

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "file_id")
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Column(name = "category_id")
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "ctime")
    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}