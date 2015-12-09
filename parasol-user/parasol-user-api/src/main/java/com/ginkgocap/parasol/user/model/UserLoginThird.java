package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	private Long id;
	/**
	 * 个人用户id和组织用户id.
	 */
	private Long userId;
	/**
	 * openId即为tb_user表中的通行证。.
	 */
	private String openId;
	/**
	 * 登录类型 100:QQ登录，200:新浪微博登录.
	 */
	private int loginType;
	/**
	 * 过期时间.
	 */
	private int expiresday;
	/**
	 * 访问会话.
	 */
	private String accesstoken;
	/**
	 * 头像地址.
	 */
	private String headPic;
	/**
	 * 创建时间.
	 */
	private Long ctime;
	/**
	 * 修改时间.
	 */
	private Long utime;
	/**
	 * 用户IP.
	 */
	private String ip;

	public UserLoginThird() {
	}

	public UserLoginThird(Long id, Long userId, String openId, int loginType,
			String accesstoken, Long ctime, Long utime, String ip) {
		this.id = id;
		this.userId = userId;
		this.openId = openId;
		this.loginType = loginType;
		this.accesstoken = accesstoken;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	public UserLoginThird(Long id, Long userId, String openId, int loginType,
			int expiresday, String accesstoken, Long ctime, Long utime,
			String ip) {
		this.id = id;
		this.userId = userId;
		this.openId = openId;
		this.loginType = loginType;
		this.expiresday = expiresday;
		this.accesstoken = accesstoken;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator")
	@Column(name = "id")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "open_id", nullable = false, length = 100)
	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "login_type", nullable = false)
	public int getLoginType() {
		return this.loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	@Column(name = "expiresday")
	public int getExpiresday() {
		return this.expiresday;
	}

	public void setExpiresday(int expiresday) {
		this.expiresday = expiresday;
	}

	@Column(name = "accesstoken", nullable = false, length = 100)
	public String getAccesstoken() {
		return this.accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	@Column(name = "head_pic", nullable = false, length = 200)
	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
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

	@Column(name = "ip", nullable = false, length = 16)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
