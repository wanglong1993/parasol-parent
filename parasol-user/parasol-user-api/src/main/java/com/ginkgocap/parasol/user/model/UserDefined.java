package com.ginkgocap.parasol.user.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private long id;
	/**
	 * 用户id.
	 */
	private long userId;
	/**
	 * 自定义模块名.
	 */
	private String userDefinedModel;
	/**
	 * 自定义字段名.
	 */
	private String userDefinedFiled;
	/**
	 * 自定义字段内容.
	 */
	private char userDefinedValue;
	/**
	 * 创建时间.
	 */
	private Date ctime;
	/**
	 * 修改时间.
	 */
	private Date utime;
	/**
	 * 用户IP.
	 */
	private String ip;

	public UserDefined() {
	}

	public UserDefined(long id, long userId, char userDefinedValue, Date ctime,
			Date utime, String ip) {
		this.id = id;
		this.userId = userId;
		this.userDefinedValue = userDefinedValue;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	public UserDefined(long id, long userId, String userDefinedModel,
			String userDefinedFiled, char userDefinedValue, Date ctime,
			Date utime, String ip) {
		this.id = id;
		this.userId = userId;
		this.userDefinedModel = userDefinedModel;
		this.userDefinedFiled = userDefinedFiled;
		this.userDefinedValue = userDefinedValue;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "user_defined_model")
	public String getUserDefinedModel() {
		return this.userDefinedModel;
	}

	public void setUserDefinedModel(String userDefinedModel) {
		this.userDefinedModel = userDefinedModel;
	}

	@Column(name = "user_defined_filed")
	public String getUserDefinedFiled() {
		return this.userDefinedFiled;
	}

	public void setUserDefinedFiled(String userDefinedFiled) {
		this.userDefinedFiled = userDefinedFiled;
	}

	@Column(name = "user_defined_value", nullable = false, length = 1)
	public char getUserDefinedValue() {
		return this.userDefinedValue;
	}

	public void setUserDefinedValue(char userDefinedValue) {
		this.userDefinedValue = userDefinedValue;
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

	@Column(name = "ip", nullable = false, length = 16)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
