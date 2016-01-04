package com.ginkgocap.parasol.oauth2.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;


/**
 * refresh_toke
 */
@Entity
@Table(name = "oauth_refresh_token", catalog = "parasol_oauth2")
public class OauthRefreshToken implements java.io.Serializable,OAuth2RefreshToken {
	private static final long serialVersionUID = 914967629530462926L;

	private String value;

	/**
	 * Create a new refresh token.
	 */
	public OauthRefreshToken(String value) {
		this.value = value;
	}

	@Transient
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return getValue();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OauthRefreshToken)) {
			return false;
		}

		OauthRefreshToken that = (OauthRefreshToken) o;

		if (value != null ? !value.equals(that.value) : that.value != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
	
	private Long id;
	
	/**
	 * 该字段的值是将refresh_token的值通过MD5加密后存储的..
	 */
	private String tokenId;
	/**
	 * 存储将OAuth2RefreshToken.java对象序列化后的二进制数据..
	 */
	private byte[] token;
	/**
	 * 存储将OAuth2Authentication.java对象序列化后的二进制数据..
	 */
	private byte[] authentication;
	/**
	 * 数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段).
	 */
	private Date createTime;

	public OauthRefreshToken() {
	}

	public OauthRefreshToken(Date createTime) {
		this.createTime = createTime;
	}

	public OauthRefreshToken(String tokenId, byte[] token,
			byte[] authentication, Date createTime) {
		this.tokenId = tokenId;
		this.token = token;
		this.authentication = authentication;
		this.createTime = createTime;
	}

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator")
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "token_id")
	public String getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@Column(name = "token")
	public byte[] getToken() {
		return this.token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	@Column(name = "authentication")
	public byte[] getAuthentication() {
		return this.authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
