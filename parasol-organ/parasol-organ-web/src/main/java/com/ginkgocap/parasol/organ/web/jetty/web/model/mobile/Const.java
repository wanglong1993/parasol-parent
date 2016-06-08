/**
 * Copyright (c) 2011 银杏资本.
 * All Rights Reserved. 保留所有权利.
 */
package com.ginkgocap.parasol.organ.web.jetty.web.model.mobile;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class Const implements Serializable {
	
	private static final long serialVersionUID = -1952086533379937984L;

	private List<MoneyType> listMoneyType;//币种
	
	private List<String> listMoneyRange;//用户名称,如果用户信息没完善，该 person对象整体为null
	
	private List<InvestType> listInInvestType;//投资类型
	
	private List<InvestType> listOutInvestType;//融资类类型
	
	private List<Trade> listTrade;//行业
	
	private List<Area> listArea;//地区

	public List<MoneyType> getListMoneyType() {
		return listMoneyType;
	}

	public void setListMoneyType(List<MoneyType> listMoneyType) {
		this.listMoneyType = listMoneyType;
	}

	public List<String> getListMoneyRange() {
		return listMoneyRange;
	}

	public void setListMoneyRange(List<String> listMoneyRange) {
		this.listMoneyRange = listMoneyRange;
	}

	public List<Trade> getListTrade() {
		return listTrade;
	}

	public void setListTrade(List<Trade> listTrade) {
		this.listTrade = listTrade;
	}

	public List<Area> getListArea() {
		return listArea;
	}

	public void setListArea(List<Area> listArea) {
		this.listArea = listArea;
	}

	public List<InvestType> getListInInvestType() {
		return listInInvestType;
	}

	public void setListInInvestType(List<InvestType> listInInvestType) {
		this.listInInvestType = listInInvestType;
	}

	public List<InvestType> getListOutInvestType() {
		return listOutInvestType;
	}

	public void setListOutInvestType(List<InvestType> listOutInvestType) {
		this.listOutInvestType = listOutInvestType;
	}

	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * String jsonData = j.getString("Entity");
	 * */
	public static Const getByJsonString(String jsonEntity) {
		if(jsonEntity.equals("{}")) {
			return null; //无数据判断
		}
		return JSON.parseObject(jsonEntity, Const.class);
	}
	
	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * Object jsonData = j.get("Entity");
	 * */
	public static Const getByJsonObject(Object jsonEntity) {
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
	public static List<Const> getListByJsonString(String object) {
		return JSON.parseArray(object, Const.class);
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
	public static List<Const> getListByJsonObject(Object object) {
		return getListByJsonString(object.toString());
	}
}
