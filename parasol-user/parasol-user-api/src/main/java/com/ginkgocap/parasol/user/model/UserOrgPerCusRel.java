package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 好友人脉组织客户关系
 */
@Entity
@Table(name = "tb_user_org_per_cus_rel", catalog = "parasol_user")
public class UserOrgPerCusRel implements java.io.Serializable {

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
	 * 1.个人好友，2.组织好友，3.收藏的人脉，4.保存的人脉，5.好友转人脉，6.自己创建的人脉，7.保存的客户，8.收藏的客户，9.组织转客户，10.自己创建的客户
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
	/**
	 * 目录ID.
	 */
	private Long directoryId;
	/**
	 * 标签ID.
	 */
	private Long tagId;

	public UserOrgPerCusRel() {
	}

	public UserOrgPerCusRel(Long id, Long userId, Long friendId, 
			Byte releationType, Long ctime, Long utime) {
		this.id = id;
		this.userId = userId;
		this.friendId = friendId;
		this.releationType = releationType;
		this.ctime = ctime;
		this.utime = utime;
	}

	public UserOrgPerCusRel(Long id, Long userId, Long friendId, 
			Byte releationType, String name, Long ctime, Long utime) {
		this.id = id;
		this.userId = userId;
		this.friendId = friendId;
		this.releationType = releationType;
		this.name = name;
		this.ctime = ctime;
		this.utime = utime;
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

	@Column(name = "friend_id", nullable = false)
	public Long getFriendId() {
		return this.friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
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
	@Column(name = "directoryId")
	public Long getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(Long directoryId) {
		this.directoryId = directoryId;
	}
	@Column(name = "tagId")
	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

}
