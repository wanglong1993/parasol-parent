package com.ginkgocap.parasol.message.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 
* @Title: MessageVO.java
* @Package com.ginkgocap.parasol.message.vo
* @Description: TODO(返回消息VO)
* @author fuliwen@gintong.com  
* @date 2016年1月26日 下午2:35:10
* @version V1.0
 */
@JsonFilter("com.ginkgocap.parasol.message.vo.MessageVO")

public class MessageVO implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -8708045139473656554L;

	// 消息ID
	private long id;
	// 消息类型 1:添加好友；2：@好友；3：点赞；4：评论；5：留言
	private int type;
	// 创建者id
	private long createrId;
	// 消息体内容
	private String content;
	// 消息链接源id
	private long sourceId;
	// 消息连接源类型
	private String sourceType;
	// 消息连接源简介
	private String sourceTitle;
	// 接受者id
	private long receiverId;
	// 处理状态
	private int status;
	// 是否已读
	private int isRead;
	// 处理时间
	private long dealTime;
	// 源appId
	private long appid;
	
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
	public long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(long createrId) {
		this.createrId = createrId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getSourceId() {
		return sourceId;
	}
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceTitle() {
		return sourceTitle;
	}
	public void setSourceTitle(String sourceTitle) {
		this.sourceTitle = sourceTitle;
	}
	public long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public long getDealTime() {
		return dealTime;
	}
	public void setDealTime(long dealTime) {
		this.dealTime = dealTime;
	}
	public long getAppid() {
		return appid;
	}
	public void setAppid(long appid) {
		this.appid = appid;
	}
	
}
