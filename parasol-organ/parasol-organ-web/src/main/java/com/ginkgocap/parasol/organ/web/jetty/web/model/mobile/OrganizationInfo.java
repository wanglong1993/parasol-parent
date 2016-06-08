/**
 * Copyright (c) 2011 银杏资本.
 * All Rights Reserved. 保留所有权利.
 */
package com.ginkgocap.parasol.organ.web.jetty.web.model.mobile;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;


public class OrganizationInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3275254569155862372L;
	private String logo;
	private int state;// -1 未进行认证 0认证进行中 1认证失败 2认证成功
	
	private String failInfo;// "审核失败原因，审核失败时有效
	
	private String fullname;//机构全称
	
	private String shortName;//客户简称
	
	private String contactCardImage; // 机构联系人身份证照片
	
	private String occImage;// 组织机构代码证图片地址
	
	private String tcImage;// 营业执照图片地址
	
	private String legalPersonIDCardImage; // 法人身份证图片 ---没有客户ID 暂时用该字段存储客户id
	

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}


	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getFailInfo() {
		return failInfo;
	}

	public void setFailInfo(String failInfo) {
		this.failInfo = failInfo;
	}

	public String getContactCardImage() {
		return contactCardImage;
	}

	public void setContactCardImage(String contactCardImage) {
		this.contactCardImage = contactCardImage;
	}

	public String getOccImage() {
		return occImage;
	}

	public void setOccImage(String occImage) {
		this.occImage = occImage;
	}

	public String getTcImage() {
		return tcImage;
	}

	public void setTcImage(String tcImage) {
		this.tcImage = tcImage;
	}

	public String getLegalPersonIDCardImage() {
		return legalPersonIDCardImage;
	}

	public void setLegalPersonIDCardImage(String legalPersonIDCardImage) {
		this.legalPersonIDCardImage = legalPersonIDCardImage;
	}


	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * String jsonData = j.getString("Entity");
	 * */
	public static OrganizationInfo getByJsonString(String jsonEntity) {
		if(jsonEntity.equals("{}")) {
			return null; //无数据判断
		}
		return JSON.parseObject(jsonEntity, OrganizationInfo.class);
	}
	
	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * Object jsonData = j.get("Entity");
	 * */
	public static OrganizationInfo getByJsonObject(Object jsonEntity) {
		return getByJsonString(jsonEntity.toString());
	}
	
	/**
	 * @author zhangzhen
	 * 如果没有数据，返回空数组
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * String jsonData = j.getString("Entity");
	 * */
	public static List<OrganizationInfo> getListByJsonString(String object) {
		return JSON.parseArray(object, OrganizationInfo.class);
	}
	
	/**
	 * @author zhangzhen
	 * @CreateTime 2014-11-11
	 * 如果没有数据，返回空数组
	 * 
	 * 指导使用方法 
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * Object jsonData = j.get("EntityList");
	 * */
	public static List<OrganizationInfo> getListByJsonObject(Object object) {
		return getListByJsonString(object.toString());
	}
}
