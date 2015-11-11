package com.ginkgocap.parasol.sms.model;

import java.io.Serializable;

/**
 * 
* <p>Title: ShortMessage.java<／p> 
* <p>Description: ShortMessage Entity<／p> 

* @author fuliwen 
* @date 2015-11-11 
* @version 1.0
 */
public class ShortMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// id
	private long id;
	
	// 短信类型  1：验证码；2：邀请好友
	private int type;
	
	// 发送的手机号
	private String phoneNum;
	
	// 发送短信用户id
	private long uid;
	
	// 发送内容
	private String content;
	
	// 短信入队列时间
	private String createTime;
	
	// 短信发送时间
	private String completeTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
