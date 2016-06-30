package com.ginkgocap.parasol.file.web.jetty.web.utils;

import java.io.Serializable;

public class PageUtil implements Serializable{
	
	private static final long serialVersionUID = 8336435920949650502L;
	
	public PageUtil(){
	
	}
	
	public PageUtil(int order){
		this.order = order;
	}
	
	public PageUtil(PageUtil pu) {
		
		this.order = pu.getOrder();
		
		this.userRecordFirstWord = pu.getUserRecordFirstWord();
		this.userRecordLastWord = pu.getUserRecordLastWord();
		
		this.poepleRecordFirstWord = pu.getPoepleRecordFirstWord();
		this.poepleRecordLastWord = pu.getPoepleRecordLastWord();
		
		this.size = pu.getSize();
		
		this.stringUserRecordFirstId = pu.getStringUserRecordFirstId();
		this.stringUserRecordLastId = pu.getStringUserRecordLastId();
		this.stringPoepleRecordFirstId = pu.getStringPoepleRecordFirstId();
		this.stringPoepleRecordLastId = pu.getStringPoepleRecordLastId();
	}

	/**
	 * order = 0 首页
	 * order = -1前一页
	 * order = 1下一页
	 * order = -2尾页
	 * */ 
	private int order;
	
	/**用户记录索引 数据首条拼音记录*/
	private String userRecordFirstWord;
	
//	/**用户记录索引 数据首条id记录*/
//	private long userRecordFirstId;
	
	/**用户记录索引 数据尾条拼音记录*/
	private String userRecordLastWord;
	
//	/**用户记录索引 数据尾条id记录*/
//	private long userRecordLastId;
	
	/**人脉记录索引 数据首条拼音记录*/
	private String poepleRecordFirstWord;
	
//	/**人脉记录索引 数据首条id记录*/
//	private long poepleRecordFirstId;
	
	/**人脉记录索引 数据尾条拼音记录*/
	private String poepleRecordLastWord;
	
//	/**人脉记录索引 数据尾条id记录*/
//	private long poepleRecordLastId;
	
	/** 反馈总条数 */
	private int size;
	
	
	//web端使用字段
	private String stringUserRecordFirstId;
	
	private String stringUserRecordLastId;
	
	private String stringPoepleRecordFirstId;
	
	private String stringPoepleRecordLastId;
	
	/**
	 * order = 0 首页
	 * order = -1前一页
	 * order = 1下一页
	 * order = -2尾页
	 * */ 
	public int getOrder() {
		return order;
	}

	/**
	 * order = 0 首页
	 * order = -1前一页
	 * order = 1下一页
	 * order = -2尾页
	 * */ 
	public void setOrder(int order) {
		this.order = order;
	}

	public String getUserRecordFirstWord() {
		return userRecordFirstWord;
	}

	public void setUserRecordFirstWord(String userRecordFirstWord) {
		this.userRecordFirstWord = userRecordFirstWord;
	}

	public String getUserRecordLastWord() {
		return userRecordLastWord;
	}

	public void setUserRecordLastWord(String userRecordLastWord) {
		this.userRecordLastWord = userRecordLastWord;
	}

	public String getPoepleRecordFirstWord() {
		return poepleRecordFirstWord;
	}

	public void setPoepleRecordFirstWord(String poepleRecordFirstWord) {
		this.poepleRecordFirstWord = poepleRecordFirstWord;
	}

	public String getPoepleRecordLastWord() {
		return poepleRecordLastWord;
	}

	public void setPoepleRecordLastWord(String poepleRecordLastWord) {
		this.poepleRecordLastWord = poepleRecordLastWord;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getStringUserRecordFirstId() {
		return stringUserRecordFirstId;
	}

	public void setStringUserRecordFirstId(String stringUserRecordFirstId) {
		this.stringUserRecordFirstId = stringUserRecordFirstId;
	}

	public String getStringUserRecordLastId() {
		return stringUserRecordLastId;
	}

	public void setStringUserRecordLastId(String stringUserRecordLastId) {
		this.stringUserRecordLastId = stringUserRecordLastId;
	}

	public String getStringPoepleRecordFirstId() {
		return stringPoepleRecordFirstId;
	}

	public void setStringPoepleRecordFirstId(String stringPoepleRecordFirstId) {
		this.stringPoepleRecordFirstId = stringPoepleRecordFirstId;
	}

	public String getStringPoepleRecordLastId() {
		return stringPoepleRecordLastId;
	}

	public void setStringPoepleRecordLastId(String stringPoepleRecordLastId) {
		this.stringPoepleRecordLastId = stringPoepleRecordLastId;
	}
	
}
