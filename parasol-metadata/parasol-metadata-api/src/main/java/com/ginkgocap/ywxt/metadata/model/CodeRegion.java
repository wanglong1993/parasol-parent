/**
 * Copyright (c) 2011 银杏资本.
 * All Rights Reserved. 保留所有权利.
 */
package com.ginkgocap.ywxt.metadata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author 窦友
 * @version 创建时间：2011-11-18 下午2:09:05 地域
 */
@Entity
@Table(name = "tb_code_region")
public class CodeRegion implements Serializable {
	private static final long serialVersionUID = -1929086533379737984L;

	private long id;// id
	private String cname;// 中文名称
	private long parentId;
	private String ename;// 英文文名称
	private int orders;// 序号
	private String levelname;// 带有级次的地方名称
	private String levelcode;// 带有级次的id
	private transient int all=1; //查询全部使用
	


	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "cname")
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Column(name = "parentId")
	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "ename")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Column(name = "orders")
	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	@Transient
	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	@Transient
	public String getLevelcode() {
		return levelcode;
	}

	public void setLevelcode(String levelcode) {
		this.levelcode = levelcode;
	}
	
	@Transient
	public int getAll() {
		return all;
	}
}
