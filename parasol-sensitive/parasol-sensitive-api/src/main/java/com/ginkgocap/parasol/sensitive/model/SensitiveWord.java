package com.ginkgocap.parasol.sensitive.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
/**
 * 
* <p>Title: SensitiveWord.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-26 
* @version 1.0
 */
@Entity
@Table(name = "tb_sensitive_word")

public class SensitiveWord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7137527542296953143L;

	// 主键
	private long id ;
	// 敏感词
	private String word;
	// 级别，0一般、1普通、2高危、3更高、4再高、5再再高
	private int level;
	// 类别 0 一般 、1时政 、2涉黄
	private int type; 
	// 创建人
	private String creater;
	// 创建人姓名
	private long createrId;
 	// 创建时间
	private long ctime;
	
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "tb_sensitive_word") })
	@Column(name = "id")	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="word")
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	@Column(name = "level")
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Column(name = "type")
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@Column(name = "creater")
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	@Column(name="createrId")
	public long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(long createrId) {
		this.createrId = createrId;
	}
	
	@Column(name="ctime")
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	
}
