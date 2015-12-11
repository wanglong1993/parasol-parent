package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 个人用户基本资料
 */
@Entity
@Table(name = "tb_user_basic", catalog = "parasol_user")
public class UserBasic implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3191844990568874921L;
	/**
	 * 个人用户id.
	 */
	private Long userId;
	/**
	 * 若为组织则为全称，不可更改；若为个人则为昵称，不可修改。.
	 */
	private String name;
	/**
	 * 性别 男：1，女：2，0：保密.
	 */
	private Byte sex;
	/**
	 * 公司.
	 */
	private String companyName;
	/**
	 * 用户头像.
	 */
	private Long picId;
	/**
	 * 手机号.
	 */
	private String mobile;
	/**
	 * 1：正常；0：锁定；-1：注销 ；2： 删除.
	 */
	private Byte status;
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
	public UserBasic() {
	}


	@Id
	@Column(name = "user_id")
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

	@Column(name = "sex", nullable = false)
	public Byte getSex() {
		return this.sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	@Column(name = "company_name", length = 50)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "pic_id")
	public Long getPicId() {
		return this.picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	@Column(name = "mobile", unique = true, length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "status", nullable = false)
	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
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

}
