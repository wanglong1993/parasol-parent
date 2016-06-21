package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.ginkgocap.ywxt.organ.model.Area;
import com.ginkgocap.ywxt.organ.model.JsonObj;
import com.ginkgocap.ywxt.organ.model.profile.CustomerPersonalLine;
import com.ginkgocap.ywxt.organ.model.profile.CustomerPersonalPlate;
import com.ginkgocap.ywxt.organ.model.profile.CustomerPhone;

/**
 * 组织vo
 */

public class OrganProfileVo implements Serializable {

	private String name;// 组织客户名称
	private String shotName;
	private String type;
	private String industry;// 行业方向
	private long industryId;// 行业方向id
	private String isListing;
	private String stockNum;
	private String linkMobile;// 联系人电话
	private String linkEmail;// 联系人邮箱
	private List<CustomerPhone> phoneList;// 客户电话
	private String picLogo;// 客户头像logo
	private String banner;// 顶部图片路径
	private String discribe;// 客户描述
	private List<Long> columns;
	private Long userId;// 用户id
	private String auth;
	private String friends;// 1 等待同意 2 是好友 3不是好友
	private long comeId;// 来源组织id
	private long createById;// 创建者id
	private boolean black;// 是否是黑名单
	private int userConfig;// 用户设置 0对自己可见1 对好友可见2所有人可见
	private long loginUserId;// 当前登陆用户id
	// 自定义板块
	private List<CustomerPersonalPlate> personalPlateList;
	

	private List<CustomerPersonalLine> propertyList; // 自定义属性
	private String virtual;// 是否是组织 0 客户 1 用户注册组织 2 大数据推送的客户
	private String directory;// 目录信息
	// private String customerPermissions;//权限信息
	private List<Map<String, Object>> lableList;// 客户标签
	private List<JsonObj> industryObj; // 冗余行业备注
	private Area area;// 客户所属地区
	private String createType;// 客户创建人事用户还是组织 供查看主题使用 1 用户 2组织

	private String isCollect;// 是否已收藏 1:收藏 0:未收藏
	private long areaid; // 所在地区ID
	private String areaString; // 所在地区
	private String address;// 详细地址
	private String organAllName;// 组织全称
	private int status;// 组织状态 1:邮箱未激活 2:已激活 3:信息已登记 4:已绑定个人账号/未认证 5:认证中 6:已认证
						// 7:认证失败
	private int orgType;// 1:企业 2:政府 3:媒体 4:其他
	private String phone;// 组织/客户联系电话
	private String email;// 组织/客户邮箱
	private String linkManName;// 联系人姓名
	private String organNumber;// 组织号

    // 复杂模块
    private JSONArray  moudles;
    
	
	// 模板Id  
    private long templateId;
   
    
	private long id; // 当前客户数据的 Id  mongo 中的id

    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public String getOrganNumber() {
		return organNumber;
	}

	public void setOrganNumber(String organNumber) {
		this.organNumber = organNumber;
	}

	public int getUserConfig() {
		return userConfig;
	}

	public void setUserConfig(int userConfig) {
		this.userConfig = userConfig;
	}

	public boolean isBlack() {
		return black;
	}

	public void setBlack(boolean black) {
		this.black = black;
	}

	public long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}

	public List<CustomerPhone> getPhoneList() {
		return phoneList == null ? new ArrayList<CustomerPhone>() : phoneList;
	}

	public void setPhoneList(List<CustomerPhone> phoneList) {
		this.phoneList = phoneList == null ? new ArrayList<CustomerPhone>() : phoneList;
	}

	public long getComeId() {
		return comeId;
	}

	public void setComeId(long comeId) {
		this.comeId = comeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<CustomerPersonalLine> getPropertyList() {
		return propertyList == null ? new ArrayList<CustomerPersonalLine>() : propertyList;
	}

	public void setPropertyList(List<CustomerPersonalLine> propertyList) {
		this.propertyList = propertyList == null ? new ArrayList<CustomerPersonalLine>() : propertyList;
		;
	}



	public List<CustomerPersonalPlate> getPersonalPlateList() {
		return personalPlateList == null ? new ArrayList<CustomerPersonalPlate>() : personalPlateList;
	}

	public void setPersonalPlateList(List<CustomerPersonalPlate> personalPlateList) {
		this.personalPlateList = personalPlateList == null ? new ArrayList<CustomerPersonalPlate>() : personalPlateList;
	}

	public List<Long> getColumns() {
		return columns == null ? new ArrayList<Long>() : columns;
	}

	public void setColumns(List<Long> columns) {
		this.columns = columns == null ? new ArrayList<Long>() : columns;
	}

	public String getAuth() {
		return StringUtils.trimToEmpty(auth);
	}

	public void setAuth(String auth) {
		this.auth = StringUtils.trimToEmpty(auth);
	}

	public String getName() {
		return StringUtils.trimToEmpty(name);
	}

	public void setName(String name) {
		this.name = StringUtils.trimToEmpty(name);
	}

	public String getShotName() {
		return StringUtils.trimToEmpty(shotName);
	}

	public void setShotName(String shotName) {
		this.shotName = StringUtils.trimToEmpty(shotName);
	}

	public String getType() {
		return StringUtils.trimToEmpty(type);
	}

	public void setType(String type) {
		this.type = StringUtils.trimToEmpty(type);
	}

	public String getIsListing() {
		return isListing;
	}

	public void setIsListing(String isListing) {
		this.isListing = isListing;
	}

	public String getFriends() {
		return StringUtils.trimToEmpty(friends);
	}

	public void setFriends(String friends) {
		this.friends = StringUtils.trimToEmpty(friends);
	}

	public String getStockNum() {
		return StringUtils.trimToEmpty(stockNum);
	}

	public void setStockNum(String stockNum) {
		this.stockNum = StringUtils.trimToEmpty(stockNum);
	}

	public String getLinkMobile() {
		return StringUtils.trimToEmpty(linkMobile);
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = StringUtils.trimToEmpty(linkMobile);
	}

	public String getLinkEmail() {
		return StringUtils.trimToEmpty(linkEmail);
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = StringUtils.trimToEmpty(linkEmail);
	}

	public String getPicLogo() {
		return StringUtils.trimToEmpty(picLogo);
	}

	public void setPicLogo(String picLogo) {
		this.picLogo = StringUtils.trimToEmpty(picLogo);
	}

	public String getBanner() {
		return StringUtils.trimToEmpty(banner);
	}

	public void setBanner(String banner) {
		this.banner = StringUtils.trimToEmpty(banner);
	}

	public String getDiscribe() {
		return StringUtils.trimToEmpty(discribe);
	}

	public void setDiscribe(String discribe) {
		this.discribe = StringUtils.trimToEmpty(discribe);
	}

	public String getVirtual() {
		return StringUtils.trimToEmpty(virtual);
	}

	public void setVirtual(String virtual) {
		this.virtual = StringUtils.trimToEmpty(virtual);
	}

	public long getCreateById() {
		return createById;
	}

	public void setCreateById(long createById) {
		this.createById = createById;
	}

	public String getDirectory() {
		return StringUtils.trimToEmpty(directory);
	}

	public void setDirectory(String directory) {
		this.directory = StringUtils.trimToEmpty(directory);
	}

	/*
	 * public String getCustomerPermissions() { return
	 * StringUtils.trimToEmpty(customerPermissions); } public void
	 * setCustomerPermissions(String customerPermissions) {
	 * this.customerPermissions = StringUtils.trimToEmpty(customerPermissions);
	 * }
	 */

	public List<JsonObj> getIndustryObj() {
		return industryObj;
	}

	public List<Map<String, Object>> getLableList() {
		return lableList == null ? new ArrayList<Map<String, Object>>() : lableList;
	}

	public void setLableList(List<Map<String, Object>> lableList) {
		this.lableList = lableList;
	}

	public void setIndustryObj(List<JsonObj> industryObj) {
		this.industryObj = industryObj;
	}

	public Area getArea() {
		return area == null ? new Area() : area;
	}

	public void setArea(Area area) {
		this.area = area == null ? new Area() : area;
	}

	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public String getIsCollect() {
		return StringUtils.trimToEmpty(isCollect);
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public long getIndustryId() {
		return industryId;
	}

	public void setIndustryId(long industryId) {
		this.industryId = industryId;
	}

	public long getAreaid() {
		return areaid;
	}

	public void setAreaid(long areaid) {
		this.areaid = areaid;
	}

	public String getAreaString() {
		return StringUtils.trimToEmpty(areaString);
	}

	public void setAreaString(String areaString) {
		this.areaString = areaString;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrganAllName() {
		return StringUtils.trimToEmpty(organAllName);
	}

	public void setOrganAllName(String organAllName) {
		this.organAllName = organAllName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOrgType() {
		return orgType;
	}

	public void setOrgType(int orgType) {
		this.orgType = orgType;
	}

	public String getPhone() {
		return StringUtils.trimToEmpty(phone);
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return StringUtils.trimToEmpty(email);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLinkManName() {
		return StringUtils.trimToEmpty(linkManName);
	}

	public void setLinkManName(String linkManName) {
		this.linkManName = linkManName;
	}

	public JSONArray getMoudles() {
		return moudles;
	}

	public void setMoudles(JSONArray moudles) {
		this.moudles = moudles;
	}

	
    
	
	
}