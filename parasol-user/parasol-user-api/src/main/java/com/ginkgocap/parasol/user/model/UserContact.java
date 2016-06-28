package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 联系方式
 */
@JsonFilter("com.ginkgocap.parasol.user.model.UserContact")
@Entity
@Table(name = "tb_user_contact", catalog = "parasol_user")
public class UserContact implements java.io.Serializable {

	/**
	 * 
	 */
	private static final Long serialVersionUID = -6953363902667088290L;
	/**
	 * 联系方式id
	 */
	private long id;
	/**
	 * 应用id
	 */
	private long appId;
	/**
	 * 个人用户id.
	 */
	private Long userId;
	/**
	 * 联系方式名称
	 */
	private String name;
	/**
	 * 联系方式值（内容）
	 */
	private String content;
	/**
	 * 父类型：1-手机类型，2-固话类型，3-传真类型，4-邮箱类型，5-主页类型，6-即时通讯类型，7-地址, 9-自定义
	 */
	private String type;
	/**
	 * 子类型
	 *
	 *  手机类型：1-手机，2-电话，3-商务电话，4-主要电话，N-自定义
	 *  固话类型：1-固话，2-家庭电话，3-办公电话，4-主要电话，N-自定义
	 *  传真类型：1-住宅传真，2-商务传真，N-自定义
	 *  邮箱类型：1-主要邮箱，2-商务邮箱，N-自定义
	 * 	主页类型：1-个人主页，2-商务主页，N-自定义
	 * 	通讯类型：1-QQ，2-微信，3-微博，4-Skype，5-facebook，6-twitter，N-自定义
	 * 	地址类型：1-住宅，2-商务，N-自定义
	 * 	自定义类型：1-自定义字段，N-自定义长文本
	 */
	private String subtype;
	/**
	 * 0:私密,1:好友可见,2:部分好友,3:公开
	 */
	private int permission;
	/**
	 * 部分好友可见时存放好友的id，用逗号“,”隔开。
	 */
	private String friendIds;
	/**
	 * 创建时间.
	 */
	private Long ctime;
	/**
	 * 修改时间.
	 */
	private Long utime;
	
	private String ip;
	
	@Column(name="ip",length=16)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public UserContact() {
	}

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator")
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "appId")
	public long getAppId() {
		return appId;
	}


	public void setAppId(long appId) {
		this.appId = appId;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	
	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "subtype")
	public String getSubtype() {
		return subtype;
	}


	public void setSubtype(String subtype) {
		this.subtype = subtype;
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
