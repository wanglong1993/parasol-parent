package com.ginkgocap.parasol.user.model;

/**
 * 第三方登录
 */
import java.util.Date;

public class UserLoginThird implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private String openid;
	private byte loginType;
	private Byte expiresday;
	private String accesstoken;
	private Date ctime;
	private Date utime;
	private String ipRegistered;

	public UserLoginThird() {
	}

	public UserLoginThird(int id, int userId, String openid, byte loginType,
			String accesstoken, Date ctime, Date utime, String ipRegistered) {
		this.id = id;
		this.userId = userId;
		this.openid = openid;
		this.loginType = loginType;
		this.accesstoken = accesstoken;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserLoginThird(int id, int userId, String openid, byte loginType,
			Byte expiresday, String accesstoken, Date ctime, Date utime,
			String ipRegistered) {
		this.id = id;
		this.userId = userId;
		this.openid = openid;
		this.loginType = loginType;
		this.expiresday = expiresday;
		this.accesstoken = accesstoken;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public byte getLoginType() {
		return this.loginType;
	}

	public void setLoginType(byte loginType) {
		this.loginType = loginType;
	}

	public Byte getExpiresday() {
		return this.expiresday;
	}

	public void setExpiresday(Byte expiresday) {
		this.expiresday = expiresday;
	}

	public String getAccesstoken() {
		return this.accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
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
