package com.ginkgocap.ywxt.metadata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 意见反馈
 * 
 * @author lk 创建时间： 2013-03-14 13:55:00
 */
@Entity
@Table(name = "tb_suggestion")
public class Suggestion implements Serializable {

	private static final long serialVersionUID = 8717806001005102256L;

    @Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "Uid")
	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	@Column(name = "User_name")
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Column(name = "AdminUid")
	public long getAdminUid() {
		return adminUid;
	}

	public void setAdminUid(long adminUid) {
		this.adminUid = adminUid;
	}

	@Column(name = "AdminUser_name")
	public String getAdminUser_name() {
		return adminUser_name;
	}

	public void setAdminUser_name(String adminUser_name) {
		this.adminUser_name = adminUser_name;
	}

	@Column(name = "ErrUrl")
	public String getErrUrl() {
		return errUrl;
	}

	public void setErrUrl(String errUrl) {
		this.errUrl = errUrl;
	}

	@Column(name = "FeedbackType")
	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	@Column(name = "ProblemTitle")
	public String getProblemTitle() {
		return problemTitle;
	}

	public void setProblemTitle(String problemTitle) {
		this.problemTitle = problemTitle;
	}

	@Column(name = "ProblemContent")
	public String getProblemContent() {
		return problemContent;
	}

	public void setProblemContent(String problemContent) {
		this.problemContent = problemContent;
	}

	@Column(name = "BrowerInfo")
	public String getBrowerInfo() {
		return browerInfo;
	}

	public void setBrowerInfo(String browerInfo) {
		this.browerInfo = browerInfo;
	}

	@Column(name = "Remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "State")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "DataSource")
	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Column(name = "Ftime")
	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

	@Column(name = "Ctime")
	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	/** id主键 */
	private long id;
	/** 反馈者uid */
	private long uid;
	/** 反馈人用户名 */
	private String user_name;
	/** 后台管理人员ID */
	private long adminUid;
	/** 后台管理人员Name */
	private String adminUser_name;
	/** 反馈人出错页面的URL链接地址 */
	private String errUrl;
	/** 五种反馈类型 1.技术错误 2.文字错误 3.其他错误 4.新建议 5.整站建议 */
	private String feedbackType;
	/** 反馈问题的标题 */
	private String problemTitle;
	/** 反馈问题的内容 */
	private String problemContent;
	/** 反馈人的浏览器信息 */
	private String browerInfo;
	/** 备注 */
	private String remark;
	/** 反馈处理状态 0.待处理 1.已处理 2.未处理 */
	private String state;
	/** 数据来源 */
	private String dataSource;
	/** 处理时间 */
	private String ftime;
	/** 反馈人提交问题及意见的时间 */
	private String ctime;
	private String contact;

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "Suggestion [id=" + id + ", uid=" + uid + ", user_name="
				+ user_name + ", adminUid=" + adminUid + ", adminUser_name="
				+ adminUser_name + ", errUrl=" + errUrl + ", feedbackType="
				+ feedbackType + ", problemTitle=" + problemTitle
				+ ", problemContent=" + problemContent + ", browerInfo="
				+ browerInfo + ", remark=" + remark + ", state=" + state
				+ ", dataSource=" + dataSource + ", ftime=" + ftime
				+ ", ctime=" + ctime + ", contact=" + contact + "]";
	}

	
	
}
