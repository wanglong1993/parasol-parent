package com.ginkgocap.ywxt.metadata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Persister;

@Entity
@Table(name = "tb_code")
public class Code implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1328788481921086194L;
	private long id;
	private String name;
	private String type;
	private int level;
	private long creator;
	private String createBy;
	private String ctime;
	private int useType;// 使用类型 0系统 1自定义
	private String nameIndex;// 简拼音
	private String nameIndexAll;// 全拼音
	private long useCount;// 热度，使用次数
	private int root;// 类型 1 投资 2 融资 3 专家
	private int industry;// 0 不是行业 1 是行业
	private String number;// 编号，1-2-3 带父id的id
	private long sysItemId;// 自定义分词对应的系统分词
	private String sysItem;// 自定义分词对应的系统分词
	private int orderNo;// 展示顺序号
	private String remark;// 提示信息

	private String synsetIds;// 合并分词
	private String synsetNames;

	private int hasChild; // 是否有下一级

	private int disabled;// '0:不禁用 1:禁用' (20150918 根据新需求添加)
	
	private transient int all = 1; // 选择全部字段使用


	/**
	 * @return the disabled
	 */
	@Column(name = "Disabled")
	public int getDisabled() {
		return disabled;
	}

	/**
	 * @param disabled
	 *            the disabled to set
	 */
	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}

	@Column(name = "Number")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "Level")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Column(name = "Creator")
	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	@Column(name = "Ctime")
	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	@Column(name = "UseType")
	public int getUseType() {
		return useType;
	}

	public void setUseType(int useType) {
		this.useType = useType;
	}

	@Column(name = "NameIndex")
	public String getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(String nameIndex) {
		this.nameIndex = nameIndex;
	}

	@Column(name = "NameIndexAll")
	public String getNameIndexAll() {
		return nameIndexAll;
	}

	public void setNameIndexAll(String nameIndexAll) {
		this.nameIndexAll = nameIndexAll;
	}

	@Column(name = "UseCount")
	public long getUseCount() {
		return useCount;
	}

	public void setUseCount(long useCount) {
		this.useCount = useCount;
	}

	@Column(name = "Root")
	public int getRoot() {
		return root;
	}

	public void setRoot(int root) {
		this.root = root;
	}

	@Column(name = "Industry")
	public int getIndustry() {
		return industry;
	}

	public void setIndustry(int industry) {
		this.industry = industry;
	}

	@Column(name = "SysItemId")
	public long getSysItemId() {
		return sysItemId;
	}

	public void setSysItemId(long sysItemId) {
		this.sysItemId = sysItemId;
	}

	@Column(name = "SysItem")
	public String getSysItem() {
		return sysItem;
	}

	public void setSysItem(String sysItem) {
		this.sysItem = sysItem;
	}

	@Column(name = "OrderNo")
	public int getOrderNo() {
		if (orderNo == 9999) {
			return 0;
		}
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		if (orderNo == 0) {
			this.orderNo = 9999;
		} else {
			this.orderNo = orderNo;
		}
	}

	@Column(name = "CreateBy")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "Remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Transient
	public String getSynsetIds() {
		return synsetIds;
	}

	public void setSynsetIds(String synsetIds) {
		this.synsetIds = synsetIds;
	}


	@Transient
	public String getSynsetNames() {
		return synsetNames;
	}
	
	@Transient
	public int getAll() {
		return all;
	}

	
	@Override
	public String toString() {
		return "Code [id=" + id + ", name=" + name + ", type=" + type
				+ ", level=" + level + ", creator=" + creator + ", createBy="
				+ createBy + ", ctime=" + ctime + ", useType=" + useType
				+ ", nameIndex=" + nameIndex + ", nameIndexAll=" + nameIndexAll
				+ ", useCount=" + useCount + ", root=" + root + ", industry="
				+ industry + ", number=" + number + ", sysItemId=" + sysItemId
				+ ", sysItem=" + sysItem + ", orderNo=" + orderNo + ", remark="
				+ remark + ", synsetIds=" + synsetIds + ", synsetNames="
				+ synsetNames + ", hasChild=" + hasChild + ", disabled="
				+ disabled + "]";
	}

	public void setSynsetNames(String synsetNames) {
		this.synsetNames = synsetNames;
	}

	@Column(name = "HasChild")
	public int getHasChild() {
		return hasChild;
	}

	public void setHasChild(int hasChild) {
		this.hasChild = hasChild;
	}

	

}
