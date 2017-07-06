package com.ginkgocap.parasol.file.model;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@JsonFilter("com.ginkgocap.parasol.file.model.UserCategory")
@Entity
@Table(name = "tb_user_category")
public class UserCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    // 用户ID
    private Long userId;
    // 目录名称
    private String categoryName;
    // 目录排序标识
    private String sortId;
    // 创建时间
    private Date ctime;
    // 父目录ID
    private Long parentId;

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
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
}