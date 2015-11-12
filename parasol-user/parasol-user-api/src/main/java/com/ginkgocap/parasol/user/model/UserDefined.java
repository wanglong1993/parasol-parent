package com.ginkgocap.parasol.user.model;

/*
 *用户自定义 
 */
import java.util.Date;

public class UserDefined implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private String userDefinedModel;
	private String userDefinedFiled;
	private char userDefinedValue;
	private Date ctime;
	private Date utime;
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

	public String getUserDefinedModel() {
		return this.userDefinedModel;
	}

	public void setUserDefinedModel(String userDefinedModel) {
		this.userDefinedModel = userDefinedModel;
	}

	public String getUserDefinedFiled() {
		return this.userDefinedFiled;
	}

	public void setUserDefinedFiled(String userDefinedFiled) {
		this.userDefinedFiled = userDefinedFiled;
	}

	public char getUserDefinedValue() {
		return this.userDefinedValue;
	}

	public void setUserDefinedValue(char userDefinedValue) {
		this.userDefinedValue = userDefinedValue;
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

	public String getIpRegistered() {
		return this.ipRegistered;
	}

	public void setIpRegistered(String ipRegistered) {
		this.ipRegistered = ipRegistered;
	}

}
