package com.ginkgocap.parasol.user.model;


import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 组织用户基本信息
 */
@Entity
@Table(name = "tb_organ_basic", catalog = "parasol_user")
public class UserOrganBasic implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2592970951379721944L;
	/**
	 * 组织用户id.
	 */
	private Long userId;
	/**
	 * 若为组织则为全称，不可更改；若为个人则为昵称，可修改。.
	 */
	private String name;
	/**
	 * 用户头像.
	 */
	private String picPath;
	/**
	 * 1：正常；0：锁定；-1：注销 ；2： 删除.
	 */
	private Byte status;
	/**
	 * 1. gintongweb、2.gintongapp.
	 */
	private String regFrom;
	/**
	 * 若为组织则为全称，不可更改；若为个人则为昵称，可修改。.
	 */
	private String brief;
	/**
	 * 简称：组织用户的简称或个人用户简称，组织用户可修改。.
	 */
	private String shortName;
	/**
	 * 证券代码
	 */
	private String stockCode;
	/**
	 * 营业职照id
	 */
	private Long businessLicencePicId;
	
	private String phone;
	/**
	 * 组织类型 金融机构 一般企业 中介机构 政府机构 期刊报纸 研究机构 电视广播 互联网媒体.
	 */
	private String orgType;
	/**
	 * 审核是否通过: 认证状态-1 未进行认证 0认证进行中 1认证失败 2认证成功.
	 */
	private Byte auth;
	/**
	 * 组织首字母.
	 */
	private String nameFirst;
	/**
	 * 简拼音.
	 */
	private String nameIndex;
	/**
	 * 全拼音.
	 */
	private String nameIndexAll;
	/**
	 * 联系人电话
	 */
	private String companyContactsMobile;
	/**
	 * 联系人姓名
	 */
	private String companyContacts;
	/**
	 * 联系人身份证正面照片id
	 */
	private Long idcardFrontPicId;
	/**
	 * 联系人身份证背面照片id
	 */
	private Long idcardBackPicId;
	/**
	 * 创建时间.
	 */
	private Long ctime;
	/**
	 * 修改时间.
	 */
	private Long utime;
	/**
	 * 用户IP.
	 */
	private String ip;
	/**
	 * 组织对象的FileIndex对象的组合
	 */
	private Map<Long ,Object> fileIndexMap;
	

	public UserOrganBasic() {
	}

	public UserOrganBasic(Long userId, String name, String picPath, Byte status,
			String regFrom, String shortName, Byte auth, String nameFirst,
			String nameIndex, String nameIndexAll, Long ctime, Long utime,
			String ip) {
		this.userId = userId;
		this.name = name;
		this.picPath = picPath;
		this.status = status;
		this.regFrom = regFrom;
		this.shortName = shortName;
		this.auth = auth;
		this.nameFirst = nameFirst;
		this.nameIndex = nameIndex;
		this.nameIndexAll = nameIndexAll;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	public UserOrganBasic(Long userId, String name, String picPath, Byte status,
			String regFrom, String brief, String shortName, String phone,
			String orgType, Byte auth, String nameFirst, String nameIndex,
			String nameIndexAll, Long ctime, Long utime, String ip) {
		this.userId = userId;
		this.name = name;
		this.picPath = picPath;
		this.status = status;
		this.regFrom = regFrom;
		this.brief = brief;
		this.shortName = shortName;
		this.phone = phone;
		this.orgType = orgType;
		this.auth = auth;
		this.nameFirst = nameFirst;
		this.nameIndex = nameIndex;
		this.nameIndexAll = nameIndexAll;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pic_path", nullable = false, length = 200)
	public String getPicPath() {
		return this.picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	@Column(name = "status", nullable = false)
	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Column(name = "regFrom", nullable = false, length = 20)
	public String getRegFrom() {
		return this.regFrom;
	}

	public void setRegFrom(String regFrom) {
		this.regFrom = regFrom;
	}

	@Column(name = "brief", length = 100)
	public String getBrief() {
		return this.brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	@Column(name = "shortName", nullable = false, length = 50)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Column(name = "stock_code", length = 20)
	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	@Column(name = "phone", length = 15)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "business_licence_pic_id", length = 20)
	public Long getBusinessLicencePicId() {
		return businessLicencePicId;
	}

	public void setBusinessLicencePicId(Long businessLicencePicId) {
		this.businessLicencePicId = businessLicencePicId;
	}
	
	@Column(name = "orgType", length = 20)
	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	@Column(name = "auth", nullable = false)
	public Byte getAuth() {
		return this.auth;
	}

	public void setAuth(Byte auth) {
		this.auth = auth;
	}

	@Column(name = "nameFirst", nullable = false, length = 1)
	public String getNameFirst() {
		return this.nameFirst;
	}

	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
	}

	@Column(name = "nameIndex", nullable = false, length = 20)
	public String getNameIndex() {
		return this.nameIndex;
	}

	public void setNameIndex(String nameIndex) {
		this.nameIndex = nameIndex;
	}

	@Column(name = "nameIndexAll", nullable = false, length = 50)
	public String getNameIndexAll() {
		return this.nameIndexAll;
	}

	public void setNameIndexAll(String nameIndexAll) {
		this.nameIndexAll = nameIndexAll;
	}

	@Column(name = "company_contacts_mobile", nullable = false, length = 15)
	public String getCompanyContactsMobile() {
		return companyContactsMobile;
	}

	public void setCompanyContactsMobile(String companyContactsMobile) {
		this.companyContactsMobile = companyContactsMobile;
	}

	@Column(name = "company_contacts", nullable = false, length = 20)
	public String getCompanyContacts() {
		return companyContacts;
	}

	public void setCompanyContacts(String companyContacts) {
		this.companyContacts = companyContacts;
	}
	
	@Column(name = "idcard_front_pic_id", nullable = false, length = 20)
	public Long getIdcardFrontPicId() {
		return idcardFrontPicId;
	}

	public void setIdcardFrontPicId(Long idcardFrontPicId) {
		this.idcardFrontPicId = idcardFrontPicId;
	}

	@Column(name = "idcard_back_pic_id", nullable = false, length = 20)
	public Long getIdcardBackPicId() {
		return idcardBackPicId;
	}

	public void setIdcardBackPicId(Long idcardBackPicId) {
		this.idcardBackPicId = idcardBackPicId;
	}

	@Column(name = "ctime", nullable = false, length = 19)
	public Long getCtime() {
		return this.ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	
	@Column(name = "utime", nullable = false, length = 19)
	public Long getUtime() {
		return this.utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

	@Column(name = "ip", nullable = false, length = 16)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Transient
	public Map<Long, Object> getFileIndexMap() {
		return fileIndexMap;
	}

	public void setFileIndexMap(Map<Long, Object> fileIndexMap) {
		this.fileIndexMap = fileIndexMap;
	}

}
