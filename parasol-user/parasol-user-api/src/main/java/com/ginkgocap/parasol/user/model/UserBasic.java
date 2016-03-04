package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	 * 是否验证邮箱,1,验证,0,未验证
	 */
	private Byte auth;	
	/**
	 * 手机号或邮箱.
	 */
	private String passport;
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
	/**
	 * 
	 */
	private String picPath;
	
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

	@Column(name = "name",  length = 100)
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
	
    @Transient
	public String getPicPath() {
		return picPath;
	}


	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}


	@Column(name = "passport", unique = true, length = 20)
	public String getPassport() {
		return this.passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}
	
	@Column(name = "auth", nullable = false)
	public Byte getAuth() {
		return auth;
	}


	public void setAuth(Byte auth) {
		this.auth = auth;
	}


	@Column(name = "status", nullable = false)
	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Column(name = "nameIndex", length = 20)
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
