package com.ginkgocap.parasol.message.model;

import org.springframework.data.annotation.Transient;

/**
 * 
* <p>Title: MessageEntity.java<／p> 
* <p>Description: message entity <／p> 

* @author fuliwen 
* @date 2015-11-18 
* @version 1.0
 */
public class MessageEntity {
	
	 // 消息实体id，long类型时间+随机数生成
	private long id;
	
	// 消息类型
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

	// 源appId
	private String appid;
	
	// 接受者id
	@Transient
	private long receiverId;
	
	// 消息状态：是否处理
	@Transient
	private int status;
	
	// 消息是否已读
	@Transient
	private int isRead;
	
	// 消息处理时间
	@Transient
	private long dealTime;

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

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
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
	
}
