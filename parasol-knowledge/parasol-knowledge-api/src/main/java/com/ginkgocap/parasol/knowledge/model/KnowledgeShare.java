package com.ginkgocap.parasol.knowledge.model;

import com.ginkgocap.parasol.knowledge.form.Friends;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * 知识分享
 * @author liuyang
 *
 */
public class KnowledgeShare implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private long userId;//分享人
	@Transient
	private String userName;
	private long knowledgeId;//对应的知识id
	private List<Long> receiverId;//接收人
	private List<String> receiverName;//接收人名称
	private String ctime;//分享时间
	private String title;
	private List<Friends> friends;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getKnowledgeId() {
		return knowledgeId;
	}
	public void setKnowledgeId(long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
	public List<Long> getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(List<Long> receiverId) {
		this.receiverId = receiverId;
	}
	public List<String> getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(List<String> receiverName) {
		this.receiverName = receiverName;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
 
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Friends> getFriends() {
		return friends;
	}
	public void setFriends(List<Friends> friends) {
		this.friends = friends;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
