package com.ginkgocap.parasol.person.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 联系方式
 */
@JsonFilter("com.ginkgocap.parasol.person.model.PersonContact")
@Entity
@Table(name = "tb_person_contact", catalog = "parasol_person")
public class PersonContact implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1467128467561709373L;
	/**
	 * 联系方式id
	 */
	private long id;
	/**
	 * 应用id
	 */
	private long appId;
	/**
	 * 个人用户id.
	 */
	private Long personId;
	/**
	 * 联系方式名称
	 */
	private String name;
	/**
	 * 联系方式值（内容）
	 */
	private String content;
	/**
	 * 父类型：1-手机类型，2-固话类型，3-传真类型，4-邮箱类型，5-主页类型，6-即时通讯类型，7-地址, 9-自定义
	 */
	private String type;
	/**
	 * 子类型
	 *
	 *  手机类型：1-手机，2-电话，3-商务电话，4-主要电话，N-自定义
	 *  固话类型：1-固话，2-家庭电话，3-办公电话，4-主要电话，N-自定义
	 *  传真类型：1-住宅传真，2-商务传真，N-自定义
	 *  邮箱类型：1-主要邮箱，2-商务邮箱，N-自定义
	 * 	主页类型：1-个人主页，2-商务主页，N-自定义
	 * 	通讯类型：1-QQ，2-微信，3-微博，4-Skype，5-facebook，6-twitter，N-自定义
	 * 	地址类型：1-住宅，2-商务，N-自定义
	 * 	自定义类型：1-自定义字段，N-自定义长文本
	 */
	private String subtype;
	/**
	 * 创建时间.
	 */
	private Long ctime;
	/**
	 * 修改时间.
	 */
	private Long utime;
	
	private String ip;
	
	@Column(name="ip",length=16)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public PersonContact() {
	}

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator")
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "person_id", nullable = false)
	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}


	@Column(name = "ctime")
	public Long getCtime() {
		return this.ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	@Column(name = "utime")
	public Long getUtime() {
		return this.utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "appId")
	public long getAppId() {
		return appId;
	}


	public void setAppId(long appId) {
		this.appId = appId;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	
	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "subtype")
	public String getSubtype() {
		return subtype;
	}


	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

}
