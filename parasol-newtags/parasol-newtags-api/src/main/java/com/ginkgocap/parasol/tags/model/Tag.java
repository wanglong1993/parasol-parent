package com.ginkgocap.parasol.tags.model;

import java.io.Serializable;


public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6606454865761504896L;

	private long id;// '标签ID'
	private long userId; // '用户ID',
	private long appId; // '应用ID',
	private long tagType;// tag的分类（比如是：知识、组织、人、图片）默认0',
	private String tagName; // '标签名称',
	private String firstIndex;
	private int sortType;

	public int getSortType() {
		return sortType;
	}

	public void setSortType(int sortType) {
		this.sortType = sortType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public long getTagType() {
		return tagType;
	}

	public void setTagType(long tagType) {
		this.tagType = tagType;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(String firstIndex) {
		this.firstIndex = firstIndex;
	}
}
