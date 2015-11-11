package com.ginkgocap.ywxt.metadata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 邮箱后缀表
 * <p>  </p>
 * @author liubang
 * @date 2015-1-14
 */


@Entity
@Table(name="tb_email_suffix")
public class EmailSuffix implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7611745362715062787L;
	private long id;	//主键
	private	String suffix;//邮箱的后缀
	private boolean status;//后缀状态 0 无效 1有效
	
    @Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="suffix")
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	@Column(name="status")
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "EmailSuffix [id=" + id + ", suffix=" + suffix + ", status="
				+ status + "]";
	}
	
	
}
