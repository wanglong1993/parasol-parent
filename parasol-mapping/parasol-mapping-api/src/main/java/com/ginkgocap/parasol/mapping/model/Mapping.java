package com.ginkgocap.parasol.mapping.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 上午9:47:29
 * @Copyright Copyright©2015 www.gintong.com
 */

@JsonFilter("com.ginkgocap.parasol.mapping.model.Mapping")
@Entity
@Table(name = "tb_mapping")
public class Mapping implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8746712290836358492L;

	private long id;// '标签ID'
	private long openId; // '用户ID',
	private long uId; // '应用ID',
	private int idType;// tag的分类（比如是：知识、组织、人、图片）默认0',

	@Id
	@GeneratedValue(generator = "MappingId")
	@GenericGenerator(name = "MappingId", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "MappingId") })
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="openId")
	public long getOpenId() {
		return openId;
	}

	public void setOpenId(long openId) {
		this.openId = openId;
	}

	@Column(name="uId")
	public long getuId() {
		return uId;
	}

	public void setuId(long uId) {
		this.uId = uId;
	}

	@Column(name="idType")
	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}

	@Override
	public String toString() {
		return "Mapping [id=" + id + ", openId=" + openId + ", uId=" + uId + ", idType=" + idType + "]";
	}

}
