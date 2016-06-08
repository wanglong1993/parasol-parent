package com.ginkgocap.parasol.organ.web.jetty.web.model.mobile;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class Workmate implements Serializable {
	
	private static final long serialVersionUID = -8384944605448943537L;
	
	private OrganizationMini organizationMini;

	private List<JTContactMini> listJTContactMini;

	public OrganizationMini getOrganizationMini() {
		return organizationMini;
	}

	public void setOrganizationMini(OrganizationMini organizationMini) {
		this.organizationMini = organizationMini;
	}

	public List<JTContactMini> getListJTContactMini() {
		return listJTContactMini;
	}

	public void setListJTContactMini(List<JTContactMini> listJTContactMini) {
		this.listJTContactMini = listJTContactMini;
	}
	
	
	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * String jsonData = j.getString("Entity");
	 * */
	public static Workmate getByJsonString(String jsonEntity) {
		if(jsonEntity.equals("{}")) {
			return null; //无数据判断
		}
		return JSON.parseObject(jsonEntity, Workmate.class);
	}
	
	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * Object jsonData = j.get("Entity");
	 * */
	public static Workmate getByJsonObject(Object jsonEntity) {
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
	public static List<Workmate> getListByJsonString(String object) {
		return JSON.parseArray(object, Workmate.class);
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
	public static List<Workmate> getListByJsonObject(Object object) {
		return getListByJsonString(object.toString());
	}
}
