package com.ginkgocap.parasol.document.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 
* @Title: DocumentAPI.java
* @Package com.ginkgocap.parasol.document.model
* @Description: TODO(开放平台文档API)
* @author fuliwen@gintong.com  
* @date 2015年12月22日 上午11:20:49
* @version V1.0
 */
public class DocumentAPI implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 6755175107258258053L;
	
	// 主键
	private long id;
	// URL地址
	private String URL;
	// 支持格式
	private String format;
	// HTTP请求方式	
	private String requestMethod;
	// 是否需要登录	
	private boolean isLogin;
	// 访问授权限制	
	private String accredit;
	// 请求参数
	private List<DocumentRequest> request;
	// 注意事项
	private String attention;
	// 返回结果
	private JSONObject results;
	// 返回字段说明
	private List<DocumentResponse> responses;
	// 其他说明
	private String other;
    // 创建时间
	private long createTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	public String getAccredit() {
		return accredit;
	}
	public void setAccredit(String accredit) {
		this.accredit = accredit;
	}
	public List<DocumentRequest> getRequest() {
		return request;
	}
	public void setRequest(List<DocumentRequest> request) {
		this.request = request;
	}
	public String getAttention() {
		return attention;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	public JSONObject getResults() {
		return results;
	}
	public void setResults(JSONObject results) {
		this.results = results;
	}
	public List<DocumentResponse> getResponses() {
		return responses;
	}
	public void setResponses(List<DocumentResponse> responses) {
		this.responses = responses;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
