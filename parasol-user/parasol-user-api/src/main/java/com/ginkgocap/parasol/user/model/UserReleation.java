package com.ginkgocap.parasol.user.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private int id;
	/**
	 * 用户ID.
	 */
	private int userId;
	/**
	 * 组织/用户/客户/人脉ID.
	 */
	private long friendId;
	/**
	 * 状态 0:待审核 1:同意.
	 */
	private int status;
	/**
	 * 1.个人好友，组织好友，2收藏的人脉，3，保存的人脉，4，好友转人脉，5，自己创建的人脉，5，保存的客户，6，收藏的客户，7，组织转客户，8，自己创建的客户.
	 */
	private byte releationType;
	/**
	 * 好友备注名.
	 */
	private String name2;
	/**
	 * 创建时间.
	 */
	private Date ctime;
	/**
	 * 成为好友时间.
	 */
	private Date utime;

	public UserReleation() {
	}

	public UserReleation(int id, int userId, long friendId, int status,
			byte releationType, Date ctime, Date utime) {
		this.id = id;
		this.userId = userId;
		this.friendId = friendId;
		this.status = status;
		this.releationType = releationType;
		this.ctime = ctime;
		this.utime = utime;
	}

	public UserReleation(int id, int userId, long friendId, int status,
			byte releationType, String name2, Date ctime, Date utime) {
		this.id = id;
		this.userId = userId;
		this.friendId = friendId;
		this.status = status;
		this.releationType = releationType;
		this.name2 = name2;
		this.ctime = ctime;
		this.utime = utime;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "friend_id", nullable = false)
	public long getFriendId() {
		return this.friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "releation_type", nullable = false)
	public byte getReleationType() {
		return this.releationType;
	}

	public void setReleationType(byte releationType) {
		this.releationType = releationType;
	}

	@Column(name = "name2", length = 100)
	public String getName2() {
		return this.name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ctime", nullable = false, length = 19)
	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "utime", nullable = false, length = 19)
	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

}
