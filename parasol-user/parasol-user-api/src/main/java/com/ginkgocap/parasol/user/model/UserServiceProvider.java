package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 用户服务商注册表
 */
@JsonFilter("com.ginkgocap.parasol.user.model.UserServiceProvider")
@Entity
@Table(name = "tb_service_provider", catalog = "parasol_user")
public class UserServiceProvider implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户id.
	 */
	private long userId;
	/**
	 * logo_id.
	 */
	private Long logoId;
	/**
	 * 服务商名.
	 */
	private String serviceName;
	/**
	 * 营业执照.
	 */
	private Long licenceId;
	/**
	 * 所在地.
	 */
	private String location;
	/**
	 * 法人.
	 */
	private String artificialPerson;
	/**
	 * 法人身份证号.
	 */
	private String artificialPersonId;
	/**
	 * 业务联系人.
	 */
	private String businessContact;
	/**
	 * 业务联系人邮箱.
	 */
	private String businessContactEmail;
	/**
	 * 业务联系人手机.
	 */
	private String businessContactMoile;
	/**
	 * 银行账号.
	 */
	private Long bankAccount;
	/**
	 * 银行户名.
	 */
	private String bankAccountName;
	/**
	 * 对公账号开户行.
	 */
	private String bankName;
	/**
	 * 服务商审核标识 0审核通过；1审不通过；2待审核；.
	 */
	private Boolean auth;
	/**
	 * 创建时间.
	 */
	private Long ctime;
	/**
	 * 修改时间.
	 */
	private Long utime;
	/**
	 * 用户ip.
	 */
	private String ip;

	public UserServiceProvider() {
	}

	public UserServiceProvider(long userId) {
		this.userId = userId;
	}



	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "logoId")
	public Long getLogoId() {
		return this.logoId;
	}

	public void setLogoId(Long logoId) {
		this.logoId = logoId;
	}

	@Column(name = "service_name", length = 20)
	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name = "licence_id")
	public Long getLicenceId() {
		return this.licenceId;
	}

	public void setLicenceId(Long licenceId) {
		this.licenceId = licenceId;
	}

	@Column(name = "location", length = 50)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "artificial_person", length = 50)
	public String getArtificialPerson() {
		return this.artificialPerson;
	}

	public void setArtificialPerson(String artificialPerson) {
		this.artificialPerson = artificialPerson;
	}

	@Column(name = "artificial_person_id", length = 18)
	public String getArtificialPersonId() {
		return this.artificialPersonId;
	}

	public void setArtificialPersonId(String artificialPersonId) {
		this.artificialPersonId = artificialPersonId;
	}

	@Column(name = "business_contact", length = 50)
	public String getBusinessContact() {
		return this.businessContact;
	}

	public void setBusinessContact(String businessContact) {
		this.businessContact = businessContact;
	}

	@Column(name = "business_contact_email", length = 30)
	public String getBusinessContactEmail() {
		return this.businessContactEmail;
	}

	public void setBusinessContactEmail(String businessContactEmail) {
		this.businessContactEmail = businessContactEmail;
	}

	@Column(name = "business_contact_moile", length = 30)
	public String getBusinessContactMoile() {
		return this.businessContactMoile;
	}

	public void setBusinessContactMoile(String businessContactMoile) {
		this.businessContactMoile = businessContactMoile;
	}

	@Column(name = "bank_account")
	public Long getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(Long bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(name = "bank_account_name", length = 20)
	public String getBankAccountName() {
		return this.bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	@Column(name = "bank_name", length = 20)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "auth")
	public Boolean getAuth() {
		return this.auth;
	}

	public void setAuth(Boolean auth) {
		this.auth = auth;
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

	@Column(name = "ip", length = 16)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}