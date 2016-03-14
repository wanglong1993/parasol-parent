package com.ginkgocap.parasol.person.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 人脉基本资料
 */
@Entity
@Table(name = "tb_person_basic", catalog = "parasol_person")
public class PersonBasic implements java.io.Serializable {

	/**
	 * 
	 */
	private static final Long serialVersionUID = 613039920240381494L;
	/**
	 * id.
	 */
	private Long id;
	/**
	 * 人脉或用户对应人脉的id.
	 */
	private Long personId;
	/**
	 * 1-用户;2-人脉.
	 */
	private Byte personType;
	/**
	 * 姓名.
	 */
	private String name;
	/**
	 * 性别:1:男;2:女;3:保密.
	 */
	private Byte gender;
	/**
	 * 姓名拼音.
	 */
	private String pinyin;
	/**
	 * 公司.
	 */
	private String company;
	/**
	 * 职位.
	 */
	private String position;
	/**
	 * 人脉头像ID.
	 */
	private Long picId;
	/**
	 * 国外的洲或国内的省.
	 */
	private Long country;
	/**
	 * 国外的国家或国内的城市.
	 */
	private Long city;
	/**
	 * 县.
	 */
	private Long county;
	/**
	 * 详细地址.
	 */
	private String address;
	/**
	 * 个人描述.
	 */
	private String remark;

	private Long firstIndustryId;

	private Long secondIndustryId;
	/**
	 * 创建人.
	 */
	private Long userId;

	private Long utime;
	/**
	 * 创建时间.
	 */
	private Long ctime;
	/**
	 * 用户IP.
	 */
	private String ip;
	/**
	 * 人脉头像地址.
	 */
	private String picPath;

	public PersonBasic() {
	}
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator")
	@Column(name = "id")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "personId")
	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	@Column(name = "personType")
	public Byte getPersonType() {
		return this.personType;
	}

	public void setPersonType(Byte personType) {
		this.personType = personType;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "gender")
	public Byte getGender() {
		return this.gender;
	}

	public void setGender(Byte gender) {
		this.gender = gender;
	}

	@Column(name = "pinyin", length = 100)
	public String getPinyin() {
		return this.pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Column(name = "company")
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "position")
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "picId")
	public Long getPicId() {
		return this.picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	@Column(name = "country")
	public Long getCountry() {
		return this.country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	@Column(name = "city")
	public Long getCity() {
		return this.city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	@Column(name = "county")
	public Long getCounty() {
		return this.county;
	}

	public void setCounty(Long county) {
		this.county = county;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "firstIndustryId")
	public Long getFirstIndustryId() {
		return this.firstIndustryId;
	}

	public void setFirstIndustryId(Long firstIndustryId) {
		this.firstIndustryId = firstIndustryId;
	}

	@Column(name = "secondIndustryId")
	public Long getSecondIndustryId() {
		return this.secondIndustryId;
	}

	public void setSecondIndustryId(Long secondIndustryId) {
		this.secondIndustryId = secondIndustryId;
	}

	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "utime", nullable = false)
	public Long getUtime() {
		return this.utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

	@Column(name = "ctime", nullable = false)
	public Long getCtime() {
		return this.ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	@Column(name = "ip", nullable = false, length = 15)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

}
