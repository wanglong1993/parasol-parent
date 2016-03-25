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
	private static final Long serialVersionUID = -6953363902667088290L;
	/**
	 * 个人用户id.
	 */
	private Long userId;
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


	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "cellphone", length = 16)
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

	@Column(name = "is_visible")
	public byte getIsVisible() {
		return this.isVisible;
	}

	public void setIsVisible(byte isVisible) {
		this.isVisible = isVisible;
	}

	@Column(name = "ctime")
	public Long getCtime() {
		return this.ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	@Column(name = "utime")
	public Long getUtime() {
		return this.utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

	@Column(name = "ip", length = 16)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
