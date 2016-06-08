package com.ginkgocap.parasol.organ.web.jetty.web.model.mobile;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class JointRequirementNode implements Serializable {

	private static final long serialVersionUID = -6708202642005261669L;

	private RequirementMini targetRequirement;

	private List<RequirementMini> listRequirementMini;

	public RequirementMini getTargetRequirement() {
		return targetRequirement;
	}

	public void setTargetRequirement(RequirementMini targetRequirement) {
		this.targetRequirement = targetRequirement;
	}

	public List<RequirementMini> getListRequirementMini() {
		return listRequirementMini;
	}

	public void setListRequirementMini(List<RequirementMini> listRequirementMini) {
		this.listRequirementMini = listRequirementMini;
	}

	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * String jsonData = j.getString("Entity");
	 * */
	public static JointRequirementNode getByJsonString(String jsonEntity) {
		if(jsonEntity.equals("{}")) {
			return null; //无数据判断
		}
		return JSON.parseObject(jsonEntity, JointRequirementNode.class);
	}
	
	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * Object jsonData = j.get("Entity");
	 * */
	public static JointRequirementNode getByJsonObject(Object jsonEntity) {
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
	public static List<JointRequirementNode> getListByJsonString(String object) {
		return JSON.parseArray(object, JointRequirementNode.class);
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
	public static List<JointRequirementNode> getListByJsonObject(Object object) {
		return getListByJsonString(object.toString());
	}
	
}
