package com.ginkgocap.parasol.organ.web.jetty.web.model.mobile;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
//import com.ginkgocap.ywxt.people.domain.model.PeopleCustomInfo;

/**
 * 自定义字段包装类
 * 
 * @author liuyang
 * 
 */
public class CustomForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private long peopleId;
	private long userId;
	/*
	private List<PeopleCustomInfo> base_ppCustom;// 基本信息
	private List<PeopleCustomInfo> tzrq_ppCustom;// 投资需求
	private List<PeopleCustomInfo> rzrq_ppCustom;// 融资需求
	private List<PeopleCustomInfo> zjxq_ppCustom;// 专家需求
	private List<PeopleCustomInfo> zjsf_ppCustom;// 专家身份
	private List<PeopleCustomInfo> datil_ppCustom;// 详细信息
	private List<PeopleCustomInfo> education_ppCustom;// 教育经历
	private List<PeopleCustomInfo> job_ppCustom;// 工作经历

	public List<PeopleCustomInfo> getBase_ppCustom() {
		return base_ppCustom;
	}

	public void setBase_ppCustom(List<PeopleCustomInfo> base_ppCustom) {
		this.base_ppCustom = base_ppCustom;
	}

	public List<PeopleCustomInfo> getTzrq_ppCustom() {
		return tzrq_ppCustom;
	}

	public void setTzrq_ppCustom(List<PeopleCustomInfo> tzrq_ppCustom) {
		this.tzrq_ppCustom = tzrq_ppCustom;
	}

	public List<PeopleCustomInfo> getRzrq_ppCustom() {
		return rzrq_ppCustom;
	}

	public void setRzrq_ppCustom(List<PeopleCustomInfo> rzrq_ppCustom) {
		this.rzrq_ppCustom = rzrq_ppCustom;
	}

	public List<PeopleCustomInfo> getZjxq_ppCustom() {
		return zjxq_ppCustom;
	}

	public void setZjxq_ppCustom(List<PeopleCustomInfo> zjxq_ppCustom) {
		this.zjxq_ppCustom = zjxq_ppCustom;
	}

	public List<PeopleCustomInfo> getZjsf_ppCustom() {
		return zjsf_ppCustom;
	}

	public void setZjsf_ppCustom(List<PeopleCustomInfo> zjsf_ppCustom) {
		this.zjsf_ppCustom = zjsf_ppCustom;
	}

	public List<PeopleCustomInfo> getDatil_ppCustom() {
		return datil_ppCustom;
	}

	public void setDatil_ppCustom(List<PeopleCustomInfo> datil_ppCustom) {
		this.datil_ppCustom = datil_ppCustom;
	}

	public List<PeopleCustomInfo> getEducation_ppCustom() {
		return education_ppCustom;
	}

	public void setEducation_ppCustom(List<PeopleCustomInfo> education_ppCustom) {
		this.education_ppCustom = education_ppCustom;
	}

	public List<PeopleCustomInfo> getJob_ppCustom() {
		return job_ppCustom;
	}

	public void setJob_ppCustom(List<PeopleCustomInfo> job_ppCustom) {
		this.job_ppCustom = job_ppCustom;
	}
*/
	public long getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(long peopleId) {
		this.peopleId = peopleId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * String jsonData = j.getString("Entity");
	 * */
	public static CustomForm getByJsonString(String jsonEntity) {
		if(jsonEntity.equals("{}")) {
			return null; //无数据判断
		}
		return JSON.parseObject(jsonEntity, CustomForm.class);
	}
	
	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * Object jsonData = j.get("Entity");
	 * */
	public static CustomForm getByJsonObject(Object jsonEntity) {
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
	public static List<CustomForm> getListByJsonString(String object) {
		return JSON.parseArray(object, CustomForm.class);
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
	public static List<CustomForm> getListByJsonObject(Object object) {
		return getListByJsonString(object.toString());
	}
	
}
