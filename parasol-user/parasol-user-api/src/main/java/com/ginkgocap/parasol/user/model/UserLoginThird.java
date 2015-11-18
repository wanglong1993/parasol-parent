package com.ginkgocap.parasol.user.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 第三方登录
 */
@Entity
@Table(name = "tb_login_third", catalog = "parasol_user")
public class UserLoginThird implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7846949044573502980L;
	/**
	 * 主键.
	 */
	private int id;
	/**
	 * 个人用户id和组织用户id.
	 */
	private int userId;
	/**
	 * openid即为tb_user表中的通行证。.
	 */
	private String openid;
	/**
	 * 登录类型 100:QQ登录，200:新浪微博登录.
	 */
	private byte loginType;
	/**
	 * 过期时间.
	 */
	private Byte expiresday;
	/**
	 * 访问会话.
	 */
	private String accesstoken;
	/**
	 * 创建时间.
	 */
	private Date ctime;
	/**
	 * 修改时间.
	 */
	private Date utime;
	/**
	 * 用户IP.
	 */
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

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "openid", nullable = false, length = 100)
	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "loginType", nullable = false)
	public byte getLoginType() {
		return this.loginType;
	}

	public void setLoginType(byte loginType) {
		this.loginType = loginType;
	}

	@Column(name = "expiresday")
	public Byte getExpiresday() {
		return this.expiresday;
	}

	public void setExpiresday(Byte expiresday) {
		this.expiresday = expiresday;
	}

	@Column(name = "accesstoken", nullable = false, length = 100)
	public String getAccesstoken() {
		return this.accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ctime", nullable = false, length = 19)
	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
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
