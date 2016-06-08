package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;
import java.util.List;

import com.ginkgocap.ywxt.organ.model.profile.CustomerPersonalLine;
import com.ginkgocap.ywxt.organ.model.resource.CustomerAddress;
/**
* <p>Title: CustomerResourceVo.java<／p> 
* <p>Description: 资源需求<／p> 
* @author wfl
* @date 2015-3-10 
* @version 1.0
 */
public class CustomerResourceVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7449445871689180585L;
	/** 主键 */
	private Long id;
	/** 所属模块： 1-投资，2-融资，3-专家需求，4-专家身份 */
	private Integer parentType;
	/** 地址 */
	private CustomerAddress address;
	/** 行业 */
	private List<String> industryIds;
	/** 行业 */
	private List<String> industryNames;
	/** 类型 */
	private List<String> typeIds;
	/** 类型 */
	private List<String> typeNames;
	/** 自定义字段 */
	private List<CustomerPersonalLine> personalLineList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getParentType() {
		return parentType;
	}

	public void setParentType(Integer parentType) {
		this.parentType = parentType;
	}


	public CustomerAddress getAddress() {
		return address;
	}

	public void setAddress(CustomerAddress address) {
		this.address = address;
	}

	public List<String> getIndustryIds() {
		return industryIds;
	}

	public void setIndustryIds(List<String> industryIds) {
		this.industryIds = industryIds;
	}

	public List<String> getIndustryNames() {
		return industryNames;
	}

	public void setIndustryNames(List<String> industryNames) {
		this.industryNames = industryNames;
	}

	public List<String> getTypeIds() {
		return typeIds;
	}

	public void setTypeIds(List<String> typeIds) {
		this.typeIds = typeIds;
	}

	public List<String> getTypeNames() {
		return typeNames;
	}

	public void setTypeNames(List<String> typeNames) {
		this.typeNames = typeNames;
	}

	public List<CustomerPersonalLine> getPersonalLineList() {
		return personalLineList;
	}

	public void setPersonalLineList(List<CustomerPersonalLine> personalLineList) {
		this.personalLineList = personalLineList;
	}

	
}
