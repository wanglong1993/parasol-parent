package com.ginkgocap.parasol.user.model;


import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 组织用户基本信息
 */
@Entity
@Table(name = "tb_organ_basic", catalog = "parasol_user")
public class UserOrganBasic implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2592970951379721944L;
	/**
	 * 组织用户id.
	 */
	private Long userId;
	/**
	 * 若为组织则为全称，不可更改；若为个人则为昵称，可修改。.
	 */
	private String name;
	/**
	 * 用户头像.
	 */
	private Long picId;
	/**
	 * 1：正常；0：锁定；-1：注销 ；2： 删除.
	 */
	private Byte status;
	/**
	 * 审核是否通过: 认证状态-1 未进行认证 0认证进行中 1认证失败 2认证成功.
	 */
	private Byte auth;
	/**
	 * 简拼音.
	 */
	private String nameIndex;
	/**
	 * 创建时间.
	 */
	private Long ctime;
	/**
	 * 修改时间.
	 */
	private Long utime;
	/**
	 * 组织对象的FileIndex对象的组合
	 */
	private Map<Long ,Object> fileIndexMap;
	

	public UserOrganBasic() {
	}

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pic_id", nullable = false, length = 200)
	public Long getPicId() {
		return this.picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	@Column(name = "status", nullable = false)
	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Column(name = "auth", nullable = false)
	public Byte getAuth() {
		return this.auth;
	}

	public void setAuth(Byte auth) {
		this.auth = auth;
	}

	@Column(name = "nameIndex", nullable = false, length = 20)
	public String getNameIndex() {
		return this.nameIndex;
	}

	public void setNameIndex(String nameIndex) {
		this.nameIndex = nameIndex;
	}

	@Column(name = "ctime", nullable = false, length = 19)
	public Long getCtime() {
		return this.ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	
	@Column(name = "utime", nullable = false, length = 19)
	public Long getUtime() {
		return this.utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

	@Transient
	public Map<Long, Object> getFileIndexMap() {
		return fileIndexMap;
	}

	public void setFileIndexMap(Map<Long, Object> fileIndexMap) {
		this.fileIndexMap = fileIndexMap;
	}

}
