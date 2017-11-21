package com.ginkgocap.parasol.metadata.model;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by xutlong on 2017/10/30.
 */
@JsonFilter("com.ginkgocap.parasol.metadata.model.NewRegion")
@Entity
@Table(name = "tb_new_region")
public class NewRegion implements Serializable {

    private String id;
    private String name;
    private String firstSpell;
    private String fullSpell;
    private Long parentId;
    private Integer sort;
    private String firstCode;
    private Integer childs;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "firstspell")
    public String getFirstSpell() {
        return firstSpell;
    }

    public void setFirstSpell(String firstSpell) {
        this.firstSpell = firstSpell;
    }

    @Column(name = "fullspell")
    public String getFullSpell() {
        return fullSpell;
    }

    public void setFullSpell(String fullSpell) {
        this.fullSpell = fullSpell;
    }

    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "first_code")
    public String getFirstCode() {
        return firstCode;
    }

    public void setFirstCode(String firstCode) {
        this.firstCode = firstCode;
    }

    @Column(name = "childs")
    public Integer getChilds() {
        return childs;
    }

    public void setChilds(Integer childs) {
        this.childs = childs;
    }
}
