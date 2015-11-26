package com.ginkgocap.parasol.sensitive.model;

import java.io.Serializable;
/**
 * 
* <p>Title: SensitiveWord.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-26 
* @version 1.0
 */
public class SensitiveWord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7137527542296953143L;
	
	private long id ;		//主键
	private String word;	//敏感词
	private int level;		// 级别，0一般、1普通、2高危、3更高、4再高、5再再高
	private int type;		//类别 0 一般 、1时政 、2涉黄 
	private String creator; //创建人
	private long creatorid; //创建人姓名
	private String ctime; 	//创建时间
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public long getCreatorid() {
		return creatorid;
	}
	public void setCreatorid(long creatorid) {
		this.creatorid = creatorid;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	
	
}
