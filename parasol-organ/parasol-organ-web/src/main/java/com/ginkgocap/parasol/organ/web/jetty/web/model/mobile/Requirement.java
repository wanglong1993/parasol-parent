/**
 * Copyright (c) 2011 银杏资本.
 * All Rights Reserved. 保留所有权利.
 */
package com.ginkgocap.parasol.organ.web.jetty.web.model.mobile;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.Utils;

public class Requirement implements Serializable {

	private static final long serialVersionUID = -7963310569100862289L;

	private int userid;// 需求发布人id，发布时为空

	private String name;// 需求发布人姓名，发布时为空
	private String userAvatar;  // 发布者需求头像
	private String publishTime;// 需求发布时间，发布时为空
	private int friendState=1;// "0-好友；1-对方请求我为好友；2-非好友；3-等待对方验证",
	private String title;// 需求标题
	private int userType=1; // "1-个人;2-机构",
	private int id;// 需求id，上传需求时，该值为null
	
	private boolean isAttention; //当前用户是否关注此需求
	
	private int type;// 0-投资，1-融资

	private int publishRange;// 0-仅自己可见，1-对所有人公开，2-对listConnections里的人可见

	private String comment;// 备注信息

	private String taskId;// 附件索引

	private List<JTFile> listJTFile;// 用户名称,如果用户信息没完善，该 person对象整体为null

	private InvestKeyword investKeyword;// 投融资意向关键字对象
	private List<RequirementMini> listMatchRequirementMini; // 匹配需求
	private List<ConnectionsMini> listMatchConnectionsMini; // 匹配关系
	private List<KnowledgeMini> listMatchKnowledgeMini;// 匹配知识
	private List<ConnectionsMini> listConnectionsMini;// 该需求公开给的对象，当publishRange
														// == 2时有效

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		if(!Utils.isNullOrEmpty(publishTime))
		publishTime=publishTime.substring(0,19);
		this.publishTime = publishTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPublishRange() {
		return publishRange;
	}

	public void setPublishRange(int publishRange) {
		this.publishRange = publishRange;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<JTFile> getListJTFile() {
		return listJTFile;
	}

	public void setListJTFile(List<JTFile> listJTFile) {
		this.listJTFile = listJTFile;
	}

	public InvestKeyword getInvestKeyword() {
		return investKeyword;
	}

	public void setInvestKeyword(InvestKeyword investKeyword) {
		this.investKeyword = investKeyword;
	}

	public List<ConnectionsMini> getListConnectionsMini() {
		return listConnectionsMini;
	}

	public void setListConnectionsMini(List<ConnectionsMini> listConnectionsMini) {
		this.listConnectionsMini = listConnectionsMini;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<RequirementMini> getListMatchRequirementMini() {
		return listMatchRequirementMini;
	}

	public void setListMatchRequirementMini(
			List<RequirementMini> listMatchRequirementMini) {
		this.listMatchRequirementMini = listMatchRequirementMini;
	}

	public List<ConnectionsMini> getListMatchConnectionsMini() {
		return listMatchConnectionsMini;
	}

	public void setListMatchConnectionsMini(
			List<ConnectionsMini> listMatchConnectionsMini) {
		this.listMatchConnectionsMini = listMatchConnectionsMini;
	}

	public List<KnowledgeMini> getListMatchKnowledgeMini() {
		return listMatchKnowledgeMini;
	}

	public void setListMatchKnowledgeMini(
			List<KnowledgeMini> listMatchKnowledgeMini) {
		this.listMatchKnowledgeMini = listMatchKnowledgeMini;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getFriendState() {
		return friendState;
	}

	public void setFriendState(int friendState) {
		this.friendState = friendState;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public boolean getIsAttention() {
		return isAttention;
	}

	public void setIsAttention(boolean isAttention) {
		this.isAttention = isAttention;
	}
	
	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * String jsonData = j.getString("Entity");
	 * */
	public static Requirement getByJsonString(String jsonEntity) {
		if(jsonEntity.equals("{}")) {
			return null; //无数据判断
		}
		return JSON.parseObject(jsonEntity, Requirement.class);
	}
	
	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * Object jsonData = j.get("Entity");
	 * */
	public static Requirement getByJsonObject(Object jsonEntity) {
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
	public static List<Requirement> getListByJsonString(String object) {
		return JSON.parseArray(object, Requirement.class);
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
	public static List<Requirement> getListByJsonObject(Object object) {
		return getListByJsonString(object.toString());
	}
	
}
