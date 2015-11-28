package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户好友关系
 */
@Entity
@Table(name = "tb_user_friendlhy", catalog = "parasol_user")
public class UserFriendly implements java.io.Serializable {

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
	 * 组织/用户ID.
	 */
	private Long friendId;
	/**
	 * 状态 0:待审核 1:同意.
	 */
	private Byte status;
	/**
	 * 创建时间.
	 */
	private Long ctime;
	/**
	 * 成为好友时间.
	 */
	private Long utime;

	public UserFriendly() {
	}

	public UserFriendly(Long id, Long userId, Long friendId, Byte status,
			Long ctime, Long utime) {
		this.id = id;
		this.userId = userId;
		this.friendId = friendId;
		this.status = status;
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
