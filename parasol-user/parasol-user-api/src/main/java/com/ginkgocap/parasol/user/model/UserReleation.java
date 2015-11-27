package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 好友人脉组织客户关系
 */
@Entity
@Table(name = "tb_r_user", catalog = "parasol_user")
public class UserReleation implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2891979471937846568L;
	/**
	 * 主键.
	 */
	private Long id;
	/**
	 * 用户ID.
	 */
	private Long userId;
	/**
	 * 组织/用户/客户/人脉ID.
	 */
	private Long friendId;
	/**
	 * 状态 0:待审核 1:同意.
	 */
	private Byte status;
	/**
	 * 1.个人好友，组织好友，2收藏的人脉，3，保存的人脉，4，好友转人脉，5，自己创建的人脉，5，保存的客户，6，收藏的客户，7，组织转客户，8，自己创建的客户.
	 */
	private Byte releationType;
	/**
	 * 好友备注名.
	 */
	private String name;
	/**
	 * 创建时间.
	 */
	private Long ctime;
	/**
	 * 成为好友时间.
	 */
	private Long utime;

	public UserReleation() {
	}

	public UserReleation(Long id, Long userId, Long friendId, Byte status,
			Byte releationType, Long ctime, Long utime) {
		this.id = id;
		this.userId = userId;
		this.friendId = friendId;
		this.status = status;
		this.releationType = releationType;
		this.ctime = ctime;
		this.utime = utime;
	}

	public UserReleation(Long id, Long userId, Long friendId, Byte status,
			Byte releationType, String name, Long ctime, Long utime) {
		this.id = id;
		this.userId = userId;
		this.friendId = friendId;
		this.status = status;
		this.releationType = releationType;
		this.name = name;
		this.ctime = ctime;
		this.utime = utime;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
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

	@Column(name = "friend_id", nullable = false)
	public Long getFriendId() {
		return this.friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	@Column(name = "status", nullable = false)
	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Column(name = "releation_type", nullable = false)
	public Byte getReleationType() {
		return this.releationType;
	}

	public void setReleationType(Byte releationType) {
		this.releationType = releationType;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

}
