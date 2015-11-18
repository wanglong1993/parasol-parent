package com.ginkgocap.parasol.user.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * 用户注册登录表
 */
@Entity
@Table(name = "tb_user_login_register", catalog = "parasol_user", uniqueConstraints = {
		@UniqueConstraint(columnNames = "passport"),
		@UniqueConstraint(columnNames = "mobile"),
		@UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "user_name") })
public class UserLoginRegister implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4229992793845063007L;
	private int id;
	/**
	 * 通行证：用户注册时使用的登录名.
	 */
	private String passport;
	/**
	 * 手机号.
	 */
	private String mobile;
	/**
	 * 邮箱.
	 */
	private String email;
	/**
	 * 用户名.
	 */
	private String userName;
	/**
	 * 密码.
	 */
	private String password;
	/**
	 * 1 组织用户，0.个人用户.
	 */
	private byte virtual;
	/**
	 * 密码加密码.
	 */
	private String salt;
	/**
	 * 来源于哪个APP注册的.
	 */
	private String source;
	/**
	 * 创建时间.
	 */
	private Date ctime2;
	/**
	 * 修改时间.
	 */
	private Date utime;
	/**
	 * 用户IP.
	 */
	private String ipRegistered;

	public UserLoginRegister() {
	}

	public UserLoginRegister(int id, String passport, String password,
			byte virtual, String salt, String source, Date ctime2, Date utime,
			String ipRegistered) {
		this.id = id;
		this.passport = passport;
		this.password = password;
		this.virtual = virtual;
		this.salt = salt;
		this.source = source;
		this.ctime2 = ctime2;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserLoginRegister(int id, String passport, String mobile,
			String email, String userName, String password, byte virtual,
			String salt, String source, Date ctime2, Date utime,
			String ipRegistered) {
		this.id = id;
		this.passport = passport;
		this.mobile = mobile;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.virtual = virtual;
		this.salt = salt;
		this.source = source;
		this.ctime2 = ctime2;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "passport", unique = true, nullable = false, length = 50)
	public String getPassport() {
		return this.passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	@Column(name = "mobile", unique = true, length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "email", unique = true, length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "user_name", unique = true, length = 32)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password", nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "virtual", nullable = false)
	public byte getVirtual() {
		return this.virtual;
	}

	public void setVirtual(byte virtual) {
		this.virtual = virtual;
	}

	@Column(name = "salt", nullable = false, length = 40)
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = "source", nullable = false, length = 20)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ctime2", nullable = false, length = 19)
	public Date getCtime2() {
		return this.ctime2;
	}

	public void setCtime2(Date ctime2) {
		this.ctime2 = ctime2;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "utime", nullable = false, length = 19)
	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	@Column(name = "ip_registered", nullable = false, length = 16)
	public String getIpRegistered() {
		return this.ipRegistered;
	}

	public void setIpRegistered(String ipRegistered) {
		this.ipRegistered = ipRegistered;
	}

}
