package com.ginkgocap.ywxt.metadata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户的标签
 * @author hdy
 * @date 2014-10-21
 */

@Entity
@Table(name="tb_user_tags")
public class UserTag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long tagId; // 标签ID
	private long userId; // 用户ID
	private String tagName;// 标签名称
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tagId")
	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	@Column(name="userId")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name="tagName")
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		return "UserTag [tagId=" + tagId + ", userId=" + userId + ", tagName="
				+ tagName + "]";
	}

}
