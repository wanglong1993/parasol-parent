package com.ginkgocap.parasol.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 联系方式
 */
@Entity
@Table(name = "tb_user_contact_way", catalog = "parasol_user")
public class UserContactWay implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 525251088829953176L;
	/**
	 * 个人用户id.
	 */
	private long userId;
	/**
	 * 手机.
	 */
	private String cellphone;
	/**
	 * 邮箱.
	 */
	private String email;
	/**
	 * 微信.
	 */
	private String weixin;
	/**
	 * QQ.
	 */
	private String qq;
	/**
	 * 微博.
	 */
	private String weibo;
	/**
	 * 好友可见 1.公开，2.好友可见.
	 */
	private byte isVisible;
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

	public UserContactWay() {
	}

	public UserContactWay(long userId, String cellphone, byte isVisible,
			Long ctime, Long utime, String ip) {
		this.userId = userId;
		this.cellphone = cellphone;
		this.isVisible = isVisible;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	public UserContactWay(long userId, String cellphone, String email,
			String weixin, String qq, String weibo, byte isVisible, Long ctime,
			Long utime, String ip) {
		this.userId = userId;
		this.cellphone = cellphone;
		this.email = email;
		this.weixin = weixin;
		this.qq = qq;
		this.weibo = weibo;
		this.isVisible = isVisible;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "cellphone", nullable = false, length = 16)
	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "weixin", length = 50)
	public String getWeixin() {
		return this.weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	@Column(name = "qq", length = 16)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "weibo", length = 50)
	public String getWeibo() {
		return this.weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	@Column(name = "is_visible", nullable = false)
	public byte getIsVisible() {
		return this.isVisible;
	}

	public void setIsVisible(byte isVisible) {
		this.isVisible = isVisible;
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
