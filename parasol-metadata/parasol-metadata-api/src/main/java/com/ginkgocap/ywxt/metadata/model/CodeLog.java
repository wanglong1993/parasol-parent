package com.ginkgocap.ywxt.metadata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分词操作日志
 * 
 * @author douyou
 *
 */

@Entity
@Table(name = "tb_code_log")
public class CodeLog implements Serializable {

	private static final long serialVersionUID = 239675242003861467L;
	private long id;
	private String createBy;// '操作人',
	private long createById;// '操作人id
	private String ctime;// '操作时间',
	private String content;// '操作内容',
	private long sysItemId;// '系统分词id',
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "createBy")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "createById")
	public long getCreateById() {
		return createById;
	}

	public void setCreateById(long createById) {
		this.createById = createById;
	}

	@Column(name = "ctime")
	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "sysItemId")
	public long getSysItemId() {
		return sysItemId;
	}

	public void setSysItemId(long sysItemId) {
		this.sysItemId = sysItemId;
	}

	@Override
	public String toString() {
		return "CodeLog [id=" + id + ", createBy=" + createBy + ", createById="
				+ createById + ", ctime=" + ctime + ", content=" + content
				+ ", sysItemId=" + sysItemId + "]";
	}
	
	
}
