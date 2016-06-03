package com.ginkgocap.parasol.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFilter;
/**
 * 附件
 */
@JsonFilter("com.ginkgocap.parasol.user.model.UserAttachment")
@Entity
@Table(name = "tb_user_attachment", catalog = "parasol_user")
public class UserAttachment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2517473660791832138L;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 应用id
	 */
	private Long appId;
	/**
	 * 附件
	 */
	private Long taskId;
	
	/**
	 * 0:私密,1:好友可见,2:部分好友,3:公开
	 */
	private int permission;
	/**
	 * 部分好友可见时存放好友的id，用逗号“,”隔开。
	 */
	private String friendIds;
	
	private Long ctime;
	
	private Long utime;
	
	private String ip;
	@Id
	@Column(name = "user_id", unique = true)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "appId")
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
	

	@Column(name="taskId")
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	@Column(name = "ctime")
	public Long getCtime() {
		return ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	@Column(name = "utime")
	public Long getUtime() {
		return utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}
	@Column(name = "ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name = "permission")
	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}
	@Column(name = "friendIds")
	public String getFriendIds() {
		return friendIds;
	}

	public void setFriendIds(String friendIds) {
		this.friendIds = friendIds;
	}

}

