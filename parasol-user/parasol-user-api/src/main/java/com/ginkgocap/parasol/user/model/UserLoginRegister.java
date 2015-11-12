package com.ginkgocap.parasol.user.model;

/**
 * 用户注册登录表
 */
import java.util.Date;

public class UserLoginRegister implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String passport;
	private String mobile;
	private String email;
	private String userName;
	private String password;
	private byte virtual;
	private String salt;
	private String source;
	private Date ctime2;
	private Date utime;
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

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassport() {
		return this.passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte getVirtual() {
		return this.virtual;
	}

	public void setVirtual(byte virtual) {
		this.virtual = virtual;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getCtime2() {
		return this.ctime2;
	}

	public void setCtime2(Date ctime2) {
		this.ctime2 = ctime2;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getIpRegistered() {
		return this.ipRegistered;
	}

	public void setIpRegistered(String ipRegistered) {
		this.ipRegistered = ipRegistered;
	}

}
