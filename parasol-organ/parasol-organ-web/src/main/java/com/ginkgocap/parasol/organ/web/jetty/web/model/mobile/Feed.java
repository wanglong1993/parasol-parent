/**
 * Copyright (c) 2013 银杏资本.
 * All Rights Reserved. 保留所有权利.
 */
package com.ginkgocap.parasol.organ.web.jetty.web.model.mobile;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 分享与任务实体类
 * @author zhangwei
 *
 */
public class Feed implements Serializable {
	
	private static final long serialVersionUID = -1929086533379737984L;

	private Long id;

	private String title; //子任务标题

	private Long parentId; //主任务id
	
    private String parentTitle;//主任务标题
	
	private Long targetId; //mysqlId
	
	private String content; //发分享内容

	private String remark; //备注

	private String industryIds; //行业id

	private String industryNames; //行业名称集合

	private String categoryIds; //类型id集合

	private String categoryNames; //类型名称集合

	private String ctime; //发布时间
	
	private String lastReplyTime; //发布时间
	
	private String cformatTime; //发布时间 格式化后的时间 

	private Long creatorId; //创建人id

	private String creator; //创建人

	private Long belongId; //负责人id

	private String belongName; //负责人姓名

	private String type; //类型 发分享、发任务、发事务沟通

	private String taskType; //任务类型  业务需求、项目、其他任务

	private int status; //状态（1正常使用 0 删除 ）
	
	private String taskStatus; //任务状态（1完成 0 未完成 ）

	private String endTime; //到期时间

	private String visible; //可见范围  所有人可见、仅项目成员可见、仅@人可见

	private String taskId; //附件组id

	private int sendSMS; //是否发送短信

	private Long incId; //公司id

	private Long deptId; //部门id
	
	private List<Long> gzUserIds; //关注人
    
    private String requirementType; //需求类型
    
    private String cur; //投资金额
    
    private String country;//国别  0 国内 1国外
    
	private String province;//省份
	
	private String city;//城市
	
	private String counties;//区县
	
	private String progress;//任务进度

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIndustryIds() {
		return industryIds;
	}

	public void setIndustryIds(String industryIds) {
		this.industryIds = industryIds;
	}

	public String getIndustryNames() {
		return industryNames;
	}

	public void setIndustryNames(String industryNames) {
		this.industryNames = industryNames;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Long getBelongId() {
		return belongId;
	}

	public void setBelongId(Long belongId) {
		this.belongId = belongId;
	}

	public String getBelongName() {
		return belongName;
	}

	public void setBelongName(String belongName) {
		this.belongName = belongName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getSendSMS() {
		return sendSMS;
	}

	public void setSendSMS(int sendSMS) {
		this.sendSMS = sendSMS;
	}

	public Long getIncId() {
		return incId;
	}

	public void setIncId(Long incId) {
		this.incId = incId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getCformatTime() {
		return cformatTime;
	}

	public void setCformatTime(String cformatTime) {
		this.cformatTime = cformatTime;
	}

	public String getLastReplyTime() {
		return lastReplyTime;
	}

	public void setLastReplyTime(String lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public List<Long> getGzUserIds() {
		return gzUserIds;
	}

	public void setGzUserIds(List<Long> gzUserIds) {
		this.gzUserIds = gzUserIds;
	}

	public String getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounties() {
		return counties;
	}

	public void setCounties(String counties) {
		this.counties = counties;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getParentTitle() {
		return parentTitle;
	}

	public void setParentTitle(String parentTitle) {
		this.parentTitle = parentTitle;
	}

	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * String jsonData = j.getString("Entity");
	 * */
	public static Feed getByJsonString(String jsonEntity) {
		if(jsonEntity.equals("{}")) {
			return null; //无数据判断
		}
		return JSON.parseObject(jsonEntity, Feed.class);
	}
	
	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * Object jsonData = j.get("Entity");
	 * */
	public static Feed getByJsonObject(Object jsonEntity) {
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
	public static List<Feed> getListByJsonString(String object) {
		return JSON.parseArray(object, Feed.class);
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
	public static List<Feed> getListByJsonObject(Object object) {
		return getListByJsonString(object.toString());
	}

	
}
