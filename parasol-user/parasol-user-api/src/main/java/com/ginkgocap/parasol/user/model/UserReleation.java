package com.ginkgocap.parasol.user.model;

/**
 * 好友人脉组织客户关系
 */
import java.util.Date;

public class UserReleation implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private long friendId;
	private int status;
	private byte releationType;
	private String name2;
	private Date ctime;
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

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getFriendId() {
		return this.friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public byte getReleationType() {
		return this.releationType;
	}

	public void setReleationType(byte releationType) {
		this.releationType = releationType;
	}

	public String getName2() {
		return this.name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

}
