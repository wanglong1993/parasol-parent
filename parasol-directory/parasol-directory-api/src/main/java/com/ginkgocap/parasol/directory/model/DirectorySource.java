package com.ginkgocap.parasol.directory.model;

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

@JsonFilter("com.ginkgocap.parasol.directory.model.DirectorySource")
@Entity
@Table(name = "tb_directory_source")
public class DirectorySource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8746712290836358492L;

	private long id; //
	private long directoryId; // 目录ID
	private long appId; // Source的应用ID
	private long userId; // 用户ID
	private long sourceId; // 资源ID
	private int sourceType; // 资源类型
	private String sourceUrl; // 资源URL
	private String sourceTitle; // 资源的title
	private String sourceData; // 资源的Data
	private String invokeMethod; // 调用方法
	private long createAt; // 创建时间

	@Id
	@GeneratedValue(generator = "DirectorySourcesId")
	@GenericGenerator(name = "DirectorySourcesId", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "DirectorySourcesId") })
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "directoryId")
	public long getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(long directoryId) {
		this.directoryId = directoryId;
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

	@Column(name = "sourceUrl")
	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	@Column(name = "sourceTitle")
	public String getSourceTitle() {
		return sourceTitle;
	}

	public void setSourceTitle(String sourceTitle) {
		this.sourceTitle = sourceTitle;
	}

	@Column(name = "sourceData")
	public String getSourceData() {
		return sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	@Column(name = "invokeMethod")
	public String getInvokeMethod() {
		return invokeMethod;
	}

	public void setInvokeMethod(String invokeMethod) {
		this.invokeMethod = invokeMethod;
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
		return "DirectorySources [id=" + id + ", directoryId=" + directoryId + ", appId=" + appId + ", userId=" + userId + ", sourceId=" + sourceId + ", sourceType=" + sourceType
				+ ", sourceUrl=" + sourceUrl + ", sourceTitle=" + sourceTitle + ", sourceData=" + sourceData + ", invokeMethod=" + invokeMethod + ", createAt=" + createAt + "]";
	}

}
