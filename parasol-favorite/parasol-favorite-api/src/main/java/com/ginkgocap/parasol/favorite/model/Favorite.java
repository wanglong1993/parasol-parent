package com.ginkgocap.parasol.favorite.model;

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
 * 收藏夹 一个应用对应自己几种不不同类型的收藏夹，比如金桐对应着有（人脉、组织、需求、知识）收藏夹。
 * 应用也可以创建自己的收藏夹，比如应用A可以创建自己应用中中不通类型的收藏夹
 * <p>
 * Example： A应用可以有类型：吃饭、住宿、爬山 收藏结构如下：</br> 
 * 应用A</br>
 * ----吃饭</br>
 * --------吃饭1</br>
 * --------吃饭2</br>
 * ----住宿</br>
 * --------住宿1</br>
 * --------住宿2</br>
 * ----爬山</br>
 * --------爬山1</br>
 * --------爬山2</br>
 * </p>
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 上午9:47:29
 * @Copyright Copyright©2015 www.gintong.com
 */

@JsonFilter("com.ginkgocap.parasol.favorite.model.Favorite")
@Entity
@Table(name = "tb_favorite")
public class Favorite implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8746712290836358492L;

	/**
	 * 应用结构
	 */
	private long appId; // 应用ID

	/**
	 * 用户结构
	 */
	private long userId; // 用户ID（Ower）
	/**
	 * 收藏结构
	 */
	private long id; // 主键',
	private long pid; // 父主键',
	private String name; // '收藏夹名称',
	private String nameIndex; // '简拼音',
	private String nameIndexAll; // '全拼音',
	private int orderNo; // '展示顺序号',
	private long typeId; // '收藏夹的类型（人脉、组织、需求、知识、会议）
	private String remark; // 描述
	private String numberCode; // 编码(存放三级，爷爷-父父-自己）
	private long updateAt; // 更新时间

	@Id
	@GeneratedValue(generator = "FavoriteId")
	@GenericGenerator(name = "FavoriteId", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "FavoriteId") })
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "pid")
	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "nameIndex")
	public String getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(String nameIndex) {
		this.nameIndex = nameIndex;
	}

	@Column(name = "nameIndexAll")
	public String getNameIndexAll() {
		return nameIndexAll;
	}

	public void setNameIndexAll(String nameIndexAll) {
		this.nameIndexAll = nameIndexAll;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "appId")
	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	@Column(name = "userId")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "orderNo")
	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "typeId")
	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	@Column(name = "numberCode")
	public String getNumberCode() {
		return numberCode;
	}

	public void setNumberCode(String numberCode) {
		this.numberCode = numberCode;
	}

	@Column(name = "updateAt")
	public long getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(long updateAt) {
		this.updateAt = updateAt;
	}

	@Override
	public String toString() {
		return "Favorite [appId=" + appId + ", userId=" + userId + ", id=" + id + ", pid=" + pid + ", name=" + name + ", nameIndex=" + nameIndex + ", nameIndexAll=" + nameIndexAll
				+ ", orderNo=" + orderNo + ", typeId=" + typeId + ", remark=" + remark + ", numberCode=" + numberCode + ", updateAt=" + updateAt + "]";
	}

}
