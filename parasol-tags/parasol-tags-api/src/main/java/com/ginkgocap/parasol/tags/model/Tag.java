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
@Table(name = "tb_tag")
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
	private int sortType; //1以字母开头1以数字开头3特殊字符开头

	@Id
	@GeneratedValue(generator = "TagId")
	@GenericGenerator(name = "TagId", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "TagId") })
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "userId")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "appId")
	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	@Column(name = "tagType")
	public long getTagType() {
		return tagType;
	}

	public void setTagType(long tagType) {
		this.tagType = tagType;
	}

	@Column(name = "tagName")
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	@Column(name = "firstIndex")
	public String getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(String firstIndex) {
		this.firstIndex = firstIndex;
	}

	@Column(name = "sortType")
	public int getSortType() {
		return sortType;
	}

	public void setSortType(int sortType) {
		this.sortType = sortType;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + (int) (appId ^ (appId >>> 32));
//		result = prime * result + (int) (id ^ (id >>> 32));
//		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
//		result = prime * result + (int) (tagType ^ (tagType >>> 32));
//		result = prime * result + (int) (userId ^ (userId >>> 32));
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Tag other = (Tag) obj;
//		if (appId != other.appId)
//			return false;
//		if (id != other.id)
//			return false;
//		if (tagName == null) {
//			if (other.tagName != null)
//				return false;
//		} else if (!tagName.equals(other.tagName))
//			return false;
//		if (tagType != other.tagType)
//			return false;
//		if (userId != other.userId)
//			return false;
//		return true;
//	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Tag tag = (Tag) o;

		if (id != tag.id) return false;
		if (userId != tag.userId) return false;
		if (appId != tag.appId) return false;
		if (tagType != tag.tagType) return false;
		if (sortType != tag.sortType) return false;
		if (!tagName.equals(tag.tagName)) return false;
		return firstIndex.equals(tag.firstIndex);
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) (userId ^ (userId >>> 32));
		result = 31 * result + (int) (appId ^ (appId >>> 32));
		result = 31 * result + (int) (tagType ^ (tagType >>> 32));
		result = 31 * result + tagName.hashCode();
		result = 31 * result + firstIndex.hashCode();
		result = 31 * result + sortType;
		return result;
	}
}
