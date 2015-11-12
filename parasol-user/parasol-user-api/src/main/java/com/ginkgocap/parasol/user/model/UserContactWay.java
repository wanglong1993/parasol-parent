package com.ginkgocap.parasol.user.model;
/**
 * 联系方式
 */

import java.util.Date;
public class UserContactWay implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String cellphone;
	private String email;
	private String weixin;
	private String qq;
	private String weibo;
	private byte isVisible;
	private Date ctime;
	private Date utime;
	private String ipRegistered;

	public UserContactWay() {
	}

	public UserContactWay(int userId, String cellphone, byte isVisible,
			Date ctime, Date utime, String ipRegistered) {
		this.userId = userId;
		this.cellphone = cellphone;
		this.isVisible = isVisible;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserContactWay(int userId, String cellphone, String email,
			String weixin, String qq, String weibo, byte isVisible, Date ctime,
			Date utime, String ipRegistered) {
		this.userId = userId;
		this.cellphone = cellphone;
		this.email = email;
		this.weixin = weixin;
		this.qq = qq;
		this.weibo = weibo;
		this.isVisible = isVisible;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeixin() {
		return this.weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeibo() {
		return this.weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public byte getIsVisible() {
		return this.isVisible;
	}

	public void setIsVisible(byte isVisible) {
		this.isVisible = isVisible;
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
