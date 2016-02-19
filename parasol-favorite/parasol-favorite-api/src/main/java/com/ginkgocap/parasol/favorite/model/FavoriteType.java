package com.ginkgocap.parasol.favorite.model;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 应用的收藏夹分类
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 上午10:49:45
 * @Copyright Copyright©2015 www.gintong.com
 */
@JsonFilter("com.ginkgocap.parasol.favorite.model.FavoriteType")
@Entity
@Table(name = "tb_favorite_type")
public class FavoriteType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5717955043686779535L;
	private Long id;
	private Long appId; // 应用ID
	private String name; // 分类名字 (人脉、组织、需求、知识），应用系统自己定义的的分类
	private Long updateAt;

	@Id
	@GeneratedValue(generator = "favoriteTypeId")
	@GenericGenerator(name = "favoriteTypeId", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "favoriteTypeId") })
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "appId")
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "updateAt")
	public Long getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Long updateAt) {
		this.updateAt = updateAt;
	}

	@Override
	public String toString() {
		return "FavoriteType [id=" + id + ", appId=" + appId + ", name=" + name + "]";
	}

}
