package com.ginkgocap.parasol.user.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户自定义
 */
@Entity
@Table(name = "tb_user_defined", catalog = "parasol_user")
public class UserDefined implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 61431291379283127L;
	/**
	 * 主键.
	 */
	private Long id;
	/**
	 * 用户id.
	 */
	private Long userId;
	/**
	 * 应用id
	 */
	private Long appId;
	

	/**
	 * 自定义字段名.
	 */
	private String userDefinedFiled;
	/**
	 * 自定义字段内容.
	 */
	private String userDefinedValue;
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
	/**
	 * 用户IP.
	 */
	private String ip;

	public UserDefined() {
	}

	public UserDefined(Long id, Long userId, String userDefinedValue, Long ctime,
			Long utime, String ip) {
		this.id = id;
		this.userId = userId;
		this.userDefinedValue = userDefinedValue;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator")
	@Column(name = "id")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "user_defined_filed")
	public String getUserDefinedFiled() {
		return this.userDefinedFiled;
	}

	public void setUserDefinedFiled(String userDefinedFiled) {
		this.userDefinedFiled = userDefinedFiled;
	}

	@Column(name = "user_defined_value", nullable = false, length = 1)
	public String getUserDefinedValue() {
		return this.userDefinedValue;
	}

	public void setUserDefinedValue(String userDefinedValue) {
		this.userDefinedValue = userDefinedValue;
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

	@Column(name = "ctime", nullable = false, length = 19)
	public Long getCtime() {
		return this.ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	
	@Column(name = "utime", nullable = false, length = 19)
	public Long getUtime() {
		return this.utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

	@Column(name = "ip", nullable = false, length = 16)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	@Column(name = "appId")
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

}
