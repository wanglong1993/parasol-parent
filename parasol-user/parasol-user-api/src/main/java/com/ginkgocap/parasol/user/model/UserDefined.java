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
	private int id;
	/**
	 * 用户id.
	 */
	private int userId;
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
	private String ipRegistered;

	public UserDefined() {
	}

	public UserDefined(int id, int userId, char userDefinedValue, Date ctime,
			Date utime, String ipRegistered) {
		this.id = id;
		this.userId = userId;
		this.userDefinedValue = userDefinedValue;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserDefined(int id, int userId, String userDefinedModel,
			String userDefinedFiled, char userDefinedValue, Date ctime,
			Date utime, String ipRegistered) {
		this.id = id;
		this.userId = userId;
		this.userDefinedModel = userDefinedModel;
		this.userDefinedFiled = userDefinedFiled;
		this.userDefinedValue = userDefinedValue;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
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

	@Column(name = "ip_registered", nullable = false, length = 16)
	public String getIpRegistered() {
		return this.ipRegistered;
	}

	public void setIpRegistered(String ipRegistered) {
		this.ipRegistered = ipRegistered;
	}

}
