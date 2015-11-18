package com.ginkgocap.parasol.message.model;

/**
 * 
* <p>Title: MessageRelation.java<／p> 
* <p>Description: message relation <／p> 

* @author fuliwen 
* @date 2015-11-18 
* @version 1.0
 */
public class MessageRelation {
	
	// 消息关联id
	private long id;
	// 消息实体id
	private long entityId;
	// 接受者id
	private long receiverId;
	// 消息状态：是否处理
	private int status;
	// 消息是否已读
	private int isRead;
	// 消息处理时间
	private long dealTime;
	// 消息类型
	private int type;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getEntityId() {
		return entityId;
	}
	public void setEntityId(long entityId) {
		this.entityId = entityId;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
