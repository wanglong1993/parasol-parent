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
 * 敏感词接口
 * 
 * @author liubang 2014年4月30日 11:33:36
 */

@Entity
@Table(name = "tb_sensitive_word")
public class SensitiveWord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7137527542296953143L;

	private long id; // 主键
	private String word; // 敏感词
	private int level; // 级别，0一般、1普通、2高危、3更高、4再高、5再再高
	private int type; // 类别 0 一般 、1时政 、2涉黄
	private String creator; // 创建人
	private long creatorid; // 创建人姓名
	private String ctime; // 创建时间
	private transient int all; // 查询全部使用 
	

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "Word")
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Column(name = "Level")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Column(name = "Type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
	
	@Transient
    public int getAll() {
		return all;
	}

}
