package com.ginkgocap.parasol.message.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 
* <p>Title: MessageEntity.java<／p> 
* <p>Description: message entity <／p> 

* @author fuliwen 
* @date 2015-11-18 
* @version 1.0
 */
@Entity
@Table(name = "tb_message_entity")
public class MessageEntity implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -8708045139473656554L;

	// 消息实体id，long类型时间+随机数生成
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

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "tb_message_entity") })
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "createrId")
	public long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(long createrId) {
		this.createrId = createrId;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "sourceId")
	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name = "sourceType")
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "sourceTitle")
	public String getSourceTitle() {
		return sourceTitle;
	}

	public void setSourceTitle(String sourceTitle) {
		this.sourceTitle = sourceTitle;
	}

	@Column(name = "appid")
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public void setDealTime(long dealTime) {
		this.dealTime = dealTime;
	}
	
}
