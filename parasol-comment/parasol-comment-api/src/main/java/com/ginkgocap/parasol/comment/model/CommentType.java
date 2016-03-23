package com.ginkgocap.parasol.comment.model;

import java.io.Serializable;

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
@JsonFilter("com.ginkgocap.parasol.comment.model.CommentType")
@Entity
@Table(name = "tb_comment_type")
public class CommentType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5717955043686779535L;
	private Long id;
	private Long appId; // 应用ID
	private String name; // 分类名字 (人脉、组织、需求、知识），应用系统自己定义的的分类
	private Long updateAt;

	@Id
	@GeneratedValue(generator = "commentTypeId")
	@GenericGenerator(name = "commentTypeId", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "commentTypeId") })
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
		return "CommentType [id=" + id + ", appId=" + appId + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentType other = (CommentType) obj;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
