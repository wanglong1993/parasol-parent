package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class PushKnowledge implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8979292310033267394L;
	
	
	private long id;		//知识id
	private String title;	//知识标题
	private String pic="";		//知识标题
	private String author;	//作者名称
	private String authorId;// 作者id
	private String cpathid;	//栏目路径
	private String ctime;	//创建时间
	private String tags;	//标签
	private String desc;	//描述
	private String type;	//类型（0-全部,1-资讯，2-投融工具，3-行业，4-经典案例，5-图书报告，6-资产管理，7-宏观，8-观点，9-判例，10-法律法规，11-文章）
	private String source;	//
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = StringUtils.trimToEmpty(title);
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = StringUtils.trimToEmpty(pic);
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = StringUtils.trimToEmpty(author);
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = StringUtils.trimToEmpty(authorId);
	}
	public String getCpathid() {
		return cpathid;
	}
	public void setCpathid(String cpathid) {
		this.cpathid = StringUtils.trimToEmpty(cpathid);
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = StringUtils.trimToEmpty(ctime);
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = StringUtils.trimToEmpty(tags);
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = StringUtils.trimToEmpty(desc);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = StringUtils.trimToEmpty(type);
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = StringUtils.trimToEmpty(source);
	}
	
	
	

}
