package com.ginkgocap.ywxt.metadata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 类型对接关系表
 * 
 * @author liubang 创建时间：
 */

@Entity
@Table(name = "tb_r_code_dock")
public class RCodeDock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3169826699949164957L;
	private long id;// 主键
	private long rzId;// 融资类型id
	private String rzName;
	private String rzNumber;// 编号
	private long tzId;// 投资类型id
	private String tzName;
	private String tzNumber;// 编号
	private String creator;// 操作人
	private long creatorid;// 操作人id
	private String ctime;// 操作时间
    @Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "RzId")
	public long getRzId() {
		return rzId;
	}

	public void setRzId(long rzId) {
		this.rzId = rzId;
	}

	@Column(name = "TzId")
	public long getTzId() {
		return tzId;
	}

	public void setTzId(long tzId) {
		this.tzId = tzId;
	}

	@Column(name = "Creator")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "Creatorid")
	public long getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(long creatorid) {
		this.creatorid = creatorid;
	}

	@Column(name = "Ctime")
	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	@Column(name = "RzName")
	public String getRzName() {
		return rzName;
	}

	public void setRzName(String rzName) {
		this.rzName = rzName;
	}

	@Column(name = "RzNumber")
	public String getRzNumber() {
		return rzNumber;
	}

	public void setRzNumber(String rzNumber) {
		this.rzNumber = rzNumber;
	}

	@Column(name = "TzName")
	public String getTzName() {
		return tzName;
	}

	public void setTzName(String tzName) {
		this.tzName = tzName;
	}

	@Column(name = "TzNumber")
	public String getTzNumber() {
		return tzNumber;
	}

	public void setTzNumber(String tzNumber) {
		this.tzNumber = tzNumber;
	}

	@Override
	public String toString() {
		return "RCodeDock [id=" + id + ", rzId=" + rzId + ", rzName=" + rzName
				+ ", rzNumber=" + rzNumber + ", tzId=" + tzId + ", tzName="
				+ tzName + ", tzNumber=" + tzNumber + ", creator=" + creator
				+ ", creatorid=" + creatorid + ", ctime=" + ctime + "]";
	}
}
