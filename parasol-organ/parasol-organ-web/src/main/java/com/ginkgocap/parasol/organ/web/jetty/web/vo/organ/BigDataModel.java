package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ginkgocap.ywxt.organ.model.bigdata.BigDataChange;
import com.ginkgocap.ywxt.organ.model.bigdata.BigDataFinfo;
import com.ginkgocap.ywxt.organ.model.bigdata.BigDataInvestment;
import com.ginkgocap.ywxt.organ.model.bigdata.BigDataPeople;
import com.ginkgocap.ywxt.organ.model.bigdata.BigDataReport;
import com.ginkgocap.ywxt.organ.model.bigdata.BigDataShareholders;

public class BigDataModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String   id;
	private Long   customerId;
	private Long   createById;
	private String name;
	private boolean isCurrent;
	private String virtual;
  	private String url;
	private String utime;
  	private String ctime;
  	private String source;
  	private String taskid;
  	private String crawl_datetime;
  	private String registration_number;
  	private String organization_code;
  	private String credit_code;
  	private String operating_state;
  	private String ctype;
  	private String set_up_time;
  	private String legal_representative;
  	private String registered_capital;
  	private String business_term;
  	private String registration_authority;
  	private String date_issue;
  	private String address;
  	private String industry_involved;
  	private String business_scope;
  	private String company_profile;
  	private String comment;
	private List<BigDataChange> change;
  	private List<BigDataPeople> people;
  	private List<BigDataShareholders> shareholders;
  	private List<BigDataReport> report;
  	private List<BigDataInvestment> investment;
  	private List<BigDataFinfo> finfo;
  	
  	
  	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getCreateById() {
		return createById;
	}
	public void setCreateById(Long createById) {
		this.createById = createById;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isCurrent() {
		return isCurrent;
	}
	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}
	public String getVirtual() {
		return virtual;
	}
	public void setVirtual(String virtual) {
		this.virtual = virtual;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUtime() {
		return utime;
	}
	public void setUtime(String utime) {
		this.utime = utime;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getCrawl_datetime() {
		return crawl_datetime;
	}
	public void setCrawl_datetime(String crawl_datetime) {
		this.crawl_datetime = crawl_datetime;
	}
	public String getRegistration_number() {
		return registration_number;
	}
	public void setRegistration_number(String registration_number) {
		this.registration_number = registration_number;
	}
	public String getOrganization_code() {
		return organization_code;
	}
	public void setOrganization_code(String organization_code) {
		this.organization_code = organization_code;
	}
	public String getCredit_code() {
		return credit_code;
	}
	public void setCredit_code(String credit_code) {
		this.credit_code = credit_code;
	}
	public String getOperating_state() {
		return operating_state;
	}
	public void setOperating_state(String operating_state) {
		this.operating_state = operating_state;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getSet_up_time() {
		return set_up_time;
	}
	public void setSet_up_time(String set_up_time) {
		this.set_up_time = set_up_time;
	}
	public String getLegal_representative() {
		return legal_representative;
	}
	public void setLegal_representative(String legal_representative) {
		this.legal_representative = legal_representative;
	}
	public String getRegistered_capital() {
		return registered_capital;
	}
	public void setRegistered_capital(String registered_capital) {
		this.registered_capital = registered_capital;
	}
	public String getBusiness_term() {
		return business_term;
	}
	public void setBusiness_term(String business_term) {
		this.business_term = business_term;
	}
	public String getRegistration_authority() {
		return registration_authority;
	}
	public void setRegistration_authority(String registration_authority) {
		this.registration_authority = registration_authority;
	}
	public String getDate_issue() {
		return date_issue;
	}
	public void setDate_issue(String date_issue) {
		this.date_issue = date_issue;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIndustry_involved() {
		return industry_involved;
	}
	public void setIndustry_involved(String industry_involved) {
		this.industry_involved = industry_involved;
	}
	public String getBusiness_scope() {
		return business_scope;
	}
	public void setBusiness_scope(String business_scope) {
		this.business_scope = business_scope;
	}
	public String getCompany_profile() {
		return company_profile;
	}
	public void setCompany_profile(String company_profile) {
		this.company_profile = company_profile;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<BigDataChange> getChange() {
		return change == null ? new ArrayList<BigDataChange>() : change;
	}
	public void setChange(List<BigDataChange> change) {
		this.change = change == null ? new ArrayList<BigDataChange>() : change;
	}
	public List<BigDataPeople> getPeople() {
		return people == null ? new ArrayList<BigDataPeople>() : people;
	}
	public void setPeople(List<BigDataPeople> people) {
		this.people = people == null ? new ArrayList<BigDataPeople>() : people;
	}
	public List<BigDataShareholders> getShareholders() {
		return shareholders == null ? new ArrayList<BigDataShareholders>() : shareholders;
	}
	public void setShareholders(List<BigDataShareholders> shareholders) {
		this.shareholders = shareholders == null ? new ArrayList<BigDataShareholders>() : shareholders;
	}
	public List<BigDataReport> getReport() {
		return report == null ? new ArrayList<BigDataReport>() : report;
	}
	public void setReport(List<BigDataReport> report) {
		this.report = report == null ? new ArrayList<BigDataReport>() : report;
	}
	public List<BigDataInvestment> getInvestment() {
		return investment == null ? new ArrayList<BigDataInvestment>() : investment;
	}
	public void setInvestment(List<BigDataInvestment> investment) {
		this.investment = investment == null ? new ArrayList<BigDataInvestment>() : investment;
	}
	public List<BigDataFinfo> getFinfo() {
		return finfo == null ? new ArrayList<BigDataFinfo>() : finfo;
	}
	public void setFinfo(List<BigDataFinfo> finfo) {
		this.finfo = finfo == null ? new ArrayList<BigDataFinfo>() : finfo;
	}
}
