package com.ginkgocap.parasol.metadata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_code_region")
public class CodeRegion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8746712290836358492L;
	/**
	 * 
	 */
	private long id;
	private long parentId; // '父节点',
	private String numberCode; // 编码
	private String cname; // '中文名',
	private String ename;// '英文名',
	private int type; 	// 0：国内，1：台湾，2：港澳台，3：马来西亚，4：国外, 参考 CodeRegionType
	private int orders = 0; // '序号',
	private String tbId; // '淘宝的编码',
	
	private transient String tbParentId ; //淘宝父ID 导数据 



	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="parentId")
	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	@Column(name="cname")
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Column(name="ename")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Column(name="type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name="orders")
	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	@Column(name="tbId")
	public String getTbId() {
		return tbId;
	}

	public void setTbId(String tbId) {
		this.tbId = tbId;
	}

	@Transient
	public String getTbParentId() {
		return tbParentId;
	}

	public void setTbParentId(String tbParentId) {
		this.tbParentId = tbParentId;
	}
	
	@Column(name="numberCode")
	public String getNumberCode() {
		return numberCode;
	}

	public void setNumberCode(String numberCode) {
		this.numberCode = numberCode;
	}

	@Override
	public String toString() {
		return "CodeRegion [id=" + id + ", parentId=" + parentId + ", cname=" + cname + ", ename=" + ename + ", type=" + type + ", orders=" + orders + ", tbId=" + tbId
				+ ", tbParentId=" + tbParentId + "]";
	}

	
	
}
