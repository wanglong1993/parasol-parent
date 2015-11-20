package com.ginkgocap.parasol.metadata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
@JsonFilter("com.ginkgocap.parasol.metadata.model.Code")
@Entity
@Table(name = "tb_code")
public class Code implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8746712290836358492L;
	/**
	 * 
	 */
	private long id; // 主键',
	private long pid; // 父主键',
	private String name; // '类型名称',
	private String nameIndex; // '简拼音',
	private String nameIndexAll; // '全拼音',
	private String remark; // 描述
	private boolean isRoot; // 是根吗
	private String levelType; // 级别类型（标示这个级别是行业或者啥）
	private int useType; // 作用类型 0 系统类型，1 自定义类型
	private int orderNo; // '展示顺序号',
	private long creator; // '创建者',
	private long ctime; // '创建时间',
	private String createBy; // '创建人姓名
	private boolean hasChild; // '是否有下级',
	private boolean disabled; // '0:不禁用 1:禁用 '
	private String numberCode; // 编码


	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Column(name = "isRoot")
	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	@Column(name = "levelType")
	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}

	@Column(name = "useType")
	public int getUseType() {
		return useType;
	}

	public void setUseType(int useType) {
		this.useType = useType;
	}

	@Column(name = "orderNo")
	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "creator")
	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	@Column(name = "ctime")
	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	@Column(name = "createBy")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "hasChild")
	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	@Column(name = "disabled")
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "numberCode")
	public String getNumberCode() {
		return numberCode;
	}

	public void setNumberCode(String numberCode) {
		this.numberCode = numberCode;
	}

	
	@Override
	public String toString() {
		return "Code [id=" + id + ", pid=" + pid + ", name=" + name + ", nameIndex=" + nameIndex + ", nameIndexAll=" + nameIndexAll + ", remark=" + remark + ", isRoot=" + isRoot
				+ ", levelType=" + levelType + ", useType=" + useType + ", orderNo=" + orderNo + ", creator=" + creator + ", ctime=" + ctime + ", createBy=" + createBy
				+ ", hasChild=" + hasChild + ", disabled=" + disabled + "]";
	}
}
