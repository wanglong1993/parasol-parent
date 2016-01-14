package com.ginkgocap.parasol.oauth2.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_sessions", catalog = "parasol_oauth2")
public class OauthSessions implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rowId;

	private String sessionId;

	private String contextPath;

	private String virtualHost;

	private String lastNode;

	private Long accessTime;

	private Long lastAccessTime;

	private Long createTime;

	private Long cookieTime;

	private Long lastSavedTime;

	private Long expiryTime;

	private Long maxInterval;

	private byte[] map;

	public OauthSessions() {
	}

	public OauthSessions(String rowId) {
		this.rowId = rowId;
	}

	public OauthSessions(String rowId, String sessionId, String contextPath,
			String virtualHost, String lastNode, Long accessTime,
			Long lastAccessTime, Long createTime, Long cookieTime,
			Long lastSavedTime, Long expiryTime, Long maxInterval, byte[] map) {
		this.rowId = rowId;
		this.sessionId = sessionId;
		this.contextPath = contextPath;
		this.virtualHost = virtualHost;
		this.lastNode = lastNode;
		this.accessTime = accessTime;
		this.lastAccessTime = lastAccessTime;
		this.createTime = createTime;
		this.cookieTime = cookieTime;
		this.lastSavedTime = lastSavedTime;
		this.expiryTime = expiryTime;
		this.maxInterval = maxInterval;
		this.map = map;
	}

	@Id
	@Column(name = "rowId", unique = true, nullable = false, length = 120)
	public String getRowId() {
		return this.rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	@Column(name = "sessionId", length = 120)
	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Column(name = "contextPath", length = 60)
	public String getContextPath() {
		return this.contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Column(name = "virtualHost", length = 60)
	public String getVirtualHost() {
		return this.virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	@Column(name = "lastNode", length = 60)
	public String getLastNode() {
		return this.lastNode;
	}

	public void setLastNode(String lastNode) {
		this.lastNode = lastNode;
	}

	@Column(name = "accessTime")
	public Long getAccessTime() {
		return this.accessTime;
	}

	public void setAccessTime(Long accessTime) {
		this.accessTime = accessTime;
	}

	@Column(name = "lastAccessTime")
	public Long getLastAccessTime() {
		return this.lastAccessTime;
	}

	public void setLastAccessTime(Long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	@Column(name = "createTime")
	public Long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	@Column(name = "cookieTime")
	public Long getCookieTime() {
		return this.cookieTime;
	}

	public void setCookieTime(Long cookieTime) {
		this.cookieTime = cookieTime;
	}

	@Column(name = "lastSavedTime")
	public Long getLastSavedTime() {
		return this.lastSavedTime;
	}

	public void setLastSavedTime(Long lastSavedTime) {
		this.lastSavedTime = lastSavedTime;
	}

	@Column(name = "expiryTime")
	public Long getExpiryTime() {
		return this.expiryTime;
	}

	public void setExpiryTime(Long expiryTime) {
		this.expiryTime = expiryTime;
	}

	@Column(name = "maxInterval")
	public Long getMaxInterval() {
		return this.maxInterval;
	}

	public void setMaxInterval(Long maxInterval) {
		this.maxInterval = maxInterval;
	}

	@Column(name = "map")
	public byte[] getMap() {
		return this.map;
	}

	public void setMap(byte[] map) {
		this.map = map;
	}

}
