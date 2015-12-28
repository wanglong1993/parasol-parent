package com.ginkgocap.parasol.tags.model;

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
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 上午9:47:29
 * @Copyright Copyright©2015 www.gintong.com
 */

@JsonFilter("com.ginkgocap.parasol.tags.model.Tag")
@Entity
@Table(name = "tb_tag_source")
public class TagSource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8746712290836358492L;

	private long id; // 'tagSources 主键',
	private long tagId; // '标签ID',
	private long appId;// 'Source 的应用ID',
	private long userId;// '创建TagSource的人',
	private long sourceId; // '资源ID',
	private int sourceType; // '资源类型 知识、人脉',
	private long createAt; // '更新时间',

	@Id
	@GeneratedValue(generator = "tagSourceId")
	@GenericGenerator(name = "tagSourceId", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "tagSourceId") })
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "tagId")
	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	@Column(name = "appId")
	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	@Column(name = "userId")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "sourceId")
	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name = "sourceType")
	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "createAt")
	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}

	@Override
	public String toString() {
		return "TagSource [id=" + id + ", tagId=" + tagId + ", appId=" + appId + ", userId=" + userId + ", sourceId=" + sourceId + ", sourceType=" + sourceType + ", createAt="
				+ createAt + "]";
	}

}
