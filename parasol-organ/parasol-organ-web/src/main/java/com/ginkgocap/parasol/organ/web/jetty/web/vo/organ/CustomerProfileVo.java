package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.ginkgocap.ywxt.organ.model.Area;
import com.ginkgocap.ywxt.organ.model.JsonObj;
import com.ginkgocap.ywxt.organ.model.profile.CustomerPersonalLine;
import com.ginkgocap.ywxt.organ.model.profile.CustomerPersonalPlate;
import com.ginkgocap.ywxt.organ.model.profile.CustomerPhone;

public class CustomerProfileVo implements Serializable{
	
	private Long customerId;
	private String name;
	private String shotName;
	private String type;
	private List<String> industrys;
	private List<String> industryIds;//行业id，多个行业 
	private String isListing;
	private String stockNum;
	private String linkMobile;
	private String linkEmail;
	private List<CustomerPhone> phoneList;//客户电话
	private String picLogo;//客户头像logo
	private String banner;//顶部图片路径
	private String discribe;//客户描述
	private List<Long> columns;
	private Long userId;//用户id
	private String auth;
	private String friends;//1  等待同意  2 是好友  3不是好友
	private long comeId;//来源组织id
	private long   createById;//创建者id
	private boolean black;//是否是黑名单
	private int userConfig;//用户设置  0对自己可见1 对好友可见2所有人可见
	private long loginUserId;//当前登陆用户id
	// 自定义板块 
	private List<CustomerPersonalPlate> personalPlateList;
	
	// 固定模块
    private List<CustomerPersonalPlate> mouduesPlateList;

    // 复杂模块
    private JSONObject  complexmodule;
    
    
	 private List<CustomerPersonalLine> propertyList; //自定义属性
	private String virtual;//是否是组织 0 客户  1 用户注册组织 2 未注册的组织 
	private String directory;//目录信息
	//private String customerPermissions;//权限信息
	private List<Map<String,Object>> lableList;//客户标签
	private List<JsonObj> industryObj; //冗余行业备注
	private Area area;//客户所属地区
	private String createType;//客户创建人事用户还是组织   供查看主题使用  1 用户  2组织
	
	private String isCollect;// 是否已收藏 1:收藏 0:未收藏
	
	// 模板Id  
    private long templateId;
    
	
	public long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
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
		return phoneList == null?new ArrayList<CustomerPhone>():phoneList;
	}
	public void setPhoneList(List<CustomerPhone> phoneList) {
		this.phoneList = phoneList == null?new ArrayList<CustomerPhone>():phoneList;
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
		return propertyList == null?new ArrayList<CustomerPersonalLine>():propertyList;
	}
	public void setPropertyList(List<CustomerPersonalLine> propertyList) {
		this.propertyList = propertyList == null?new ArrayList<CustomerPersonalLine>():propertyList;;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public List<CustomerPersonalPlate> getPersonalPlateList() {
		return personalPlateList==null?new ArrayList<CustomerPersonalPlate>():personalPlateList;
	}
	public void setPersonalPlateList(List<CustomerPersonalPlate> personalPlateList) {
		this.personalPlateList = personalPlateList==null?new ArrayList<CustomerPersonalPlate>():personalPlateList;
	}
	public List<Long> getColumns() {
		return columns==null?new ArrayList<Long>():columns;
	}
	public void setColumns(List<Long> columns) {
		this.columns = columns == null?new ArrayList<Long>():columns;
	}
	public String getAuth() {
		return  StringUtils.trimToEmpty(auth);
	}
	public void setAuth(String auth) {
		this.auth = StringUtils.trimToEmpty(auth) ;
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
	
	public List<String> getIndustrys() {
		return industrys==null?new ArrayList<String>():industrys;
	}
	public void setIndustrys(List<String> industrys) {
		this.industrys = industrys == null?new ArrayList<String>():industrys;
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
	/*public String getCustomerPermissions() {
		return StringUtils.trimToEmpty(customerPermissions);
	}
	public void setCustomerPermissions(String customerPermissions) {
		this.customerPermissions = StringUtils.trimToEmpty(customerPermissions);
	}*/
	
	public List<JsonObj> getIndustryObj() {
		return industryObj;
	}
	public List<Map<String, Object>> getLableList() {
		return lableList==null?new ArrayList<Map<String,Object>>():lableList;
	}
	public void setLableList(List<Map<String, Object>> lableList) {
		this.lableList = lableList;
	}
	public void setIndustryObj(List<JsonObj> industryObj) {
		this.industryObj = industryObj;
	}
	public List<String> getIndustryIds() {
		return industryIds;
	}
	public void setIndustryIds(List<String> industryIds) {
		this.industryIds = industryIds;
	}
	public Area getArea() {
		return area  == null?new Area():area ;
	}
	public void setArea(Area area) {
		this.area = area == null?new Area():area ;
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
	
	
	 public List<CustomerPersonalPlate> getMouduesPlateList() {
		 
			return mouduesPlateList==null?new ArrayList<CustomerPersonalPlate>():mouduesPlateList;
	}
		 
	public void setMouduesPlateList(List<CustomerPersonalPlate> mouduesPlateList) {
			this.mouduesPlateList = mouduesPlateList;
	 }
		
		
		
	public JSONObject getComplexmodule() {
		return complexmodule==null ?new JSONObject():complexmodule;
	}
	
	public void setComplexmodule(JSONObject complexmodule) {
		this.complexmodule = (complexmodule==null ?new JSONObject():complexmodule);
	}
}
