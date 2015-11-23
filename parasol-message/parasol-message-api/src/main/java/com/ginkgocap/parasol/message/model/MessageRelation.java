package com.ginkgocap.parasol.message.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 
* <p>Title: MessageRelation.java<／p> 
* <p>Description: message relation <／p> 

* @author fuliwen 
* @date 2015-11-18 
* @version 1.0
 */
@Entity
@Table(name = "tb_message_relation")
public class MessageRelation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2565570809635060021L;
	
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
	
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "tb_message_relation") })
	@Column(name = "Id")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "entityId")
	public long getEntityId() {
		return entityId;
	}
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}
	
	@Column(name = "receiverId")
	public long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}
	
	@Column(name = "status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(name = "isRead")
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	
	@Column(name = "dealTime")
	public long getDealTime() {
		return dealTime;
	}
	public void setDealTime(long dealTime) {
		this.dealTime = dealTime;
	}
	
	@Column(name = "type")
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
