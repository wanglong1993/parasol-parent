package com.ginkgocap.parasol.oauth2.model;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;



/**
 * 用户允许访问AccessToken
 */
@Entity
@Table(name = "oauth_access_token", catalog = "parasol_oauth2")
public class OauthAccessToken implements java.io.Serializable,OAuth2AccessToken {
	private static final long serialVersionUID = 914967629530462926L;

	private String value;

	private Date expiration;

	private String tokenType = BEARER_TYPE.toLowerCase();

	private OAuth2RefreshToken refreshToken;

	private Set<String> scope;

	private Map<String, Object> additionalInformation = Collections.emptyMap();

	/**
	 * Create an access token from the value provided.
	 */
	public OauthAccessToken(String value) {
		this.value = value;
	}


	/**
	 * Copy constructor for access token.
	 * 
	 * @param accessToken
	 */
	public OauthAccessToken(OAuth2AccessToken accessToken) {
		this(accessToken.getValue());
		setAdditionalInformation(accessToken.getAdditionalInformation());
		setRefreshToken(accessToken.getRefreshToken());
		setExpiration(accessToken.getExpiration());
		setScope(accessToken.getScope());
		setTokenType(accessToken.getTokenType());
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * The token value.
	 * 
	 * @return The token value.
	 */
	@Transient
	public String getValue() {
		return value;
	}

	@Transient
	public int getExpiresIn() {
		return expiration != null ? Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
				.intValue() : 0;
	}

	protected void setExpiresIn(int delta) {
		setExpiration(new Date(System.currentTimeMillis() + delta));
	}

	/**
	 * The instant the token expires.
	 * 
	 * @return The instant the token expires.
	 */
	@Transient
	public Date getExpiration() {
		return expiration;
	}

	/**
	 * The instant the token expires.
	 * 
	 * @param expiration The instant the token expires.
	 */
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	/**
	 * Convenience method for checking expiration
	 * 
	 * @return true if the expiration is befor ethe current time
	 */
	@Transient
	public boolean isExpired() {
		return expiration != null && expiration.before(new Date());
	}

	/**
	 * The token type, as introduced in draft 11 of the OAuth 2 spec. The spec doesn't define (yet) that the valid token
	 * types are, but says it's required so the default will just be "undefined".
	 * 
	 * @return The token type, as introduced in draft 11 of the OAuth 2 spec.
	 */
	@Transient
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * The token type, as introduced in draft 11 of the OAuth 2 spec.
	 * 
	 * @param tokenType The token type, as introduced in draft 11 of the OAuth 2 spec.
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * The refresh token associated with the access token, if any.
	 * 
	 * @return The refresh token associated with the access token, if any.
	 */
	@Transient
	public OAuth2RefreshToken getRefreshToken() {
		return refreshToken;
	}

	/**
	 * The refresh token associated with the access token, if any.
	 * 
	 * @param refreshToken The refresh token associated with the access token, if any.
	 */
	public void setRefreshToken(OAuth2RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * The scope of the token.
	 * 
	 * @return The scope of the token.
	 */
	@Transient
	public Set<String> getScope() {
		return scope;
	}

	/**
	 * The scope of the token.
	 * 
	 * @param scope The scope of the token.
	 */
	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && toString().equals(obj.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}

	public static OAuth2AccessToken valueOf(Map<String, String> tokenParams) {
		DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(tokenParams.get(ACCESS_TOKEN));

		if (tokenParams.containsKey(EXPIRES_IN)) {
			long expiration = 0;
			try {
				expiration = Long.parseLong(String.valueOf(tokenParams.get(EXPIRES_IN)));
			}
			catch (NumberFormatException e) {
				// fall through...
			}
			token.setExpiration(new Date(System.currentTimeMillis() + (expiration * 1000L)));
		}

		if (tokenParams.containsKey(REFRESH_TOKEN)) {
			String refresh = tokenParams.get(REFRESH_TOKEN);
			DefaultOAuth2RefreshToken refreshToken = new DefaultOAuth2RefreshToken(refresh);
			token.setRefreshToken(refreshToken);
		}

		if (tokenParams.containsKey(SCOPE)) {
			Set<String> scope = new TreeSet<String>();
			for (StringTokenizer tokenizer = new StringTokenizer(tokenParams.get(SCOPE), " ,"); tokenizer
					.hasMoreTokens();) {
				scope.add(tokenizer.nextToken());
			}
			token.setScope(scope);
		}

		if (tokenParams.containsKey(TOKEN_TYPE)) {
			token.setTokenType(tokenParams.get(TOKEN_TYPE));
		}

		return token;
	}

	/**
	 * Additional information that token granters would like to add to the token, e.g. to support new token types.
	 * 
	 * @return the additional information (default empty)
	 */
	@Transient
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}

	/**
	 * Additional information that token granters would like to add to the token, e.g. to support new token types. If
	 * the values in the map are primitive then remote communication is going to always work. It should also be safe to
	 * use maps (nested if desired), or something that is explicitly serializable by Jackson.
	 * 
	 * @param additionalInformation the additional information to set
	 */
	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = new LinkedHashMap<String, Object>(additionalInformation);
	}
	
	private Long id;	
	/**
	 * 该字段具有唯一性, 其值是根据当前的username(如果有),client_id与scope通过MD5加密生成的. 具体实现请参考DefaultAuthenticationKeyGenerator.java类..
	 */
	private String authenticationId;
	/**
	 * 该字段的值是将access_token的值通过MD5加密后存储的..
	 */
	private String tokenId;
	/**
	 * 存储将OAuth2AccessToken.java对象序列化后的二进制数据, 是真实的AccessToken的数据值..
	 */
	private byte[] token;
	/**
	 * 登录时的用户名, 若客户端没有用户名(如grant_type="client_credentials"),则该值等于client_id.
	 */
	private String userName;

	private String clientId;
	/**
	 * 存储将OAuth2Authentication.java对象序列化后的二进制数据..
	 */
	private byte[] authentication;
	/**
	 * 该字段的值是将refresh_token的值通过MD5加密后存储的..
	 */
	private String refreshToken_;
	/**
	 * 数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段).
	 */
	private Date createTime;

	public OauthAccessToken() {
	}

	public OauthAccessToken(String authenticationId, Date createTime) {
		this.authenticationId = authenticationId;
		this.createTime = createTime;
	}

	public OauthAccessToken(String authenticationId, String tokenId,
			byte[] token, String userName, String clientId,
			byte[] authentication, String refreshToken, Date createTime) {
		this.authenticationId = authenticationId;
		this.tokenId = tokenId;
		this.token = token;
		this.userName = userName;
		this.clientId = clientId;
		this.authentication = authentication;
		this.refreshToken_ = refreshToken;
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
	
	@Column(name = "authentication_id", unique = true, nullable = false)
	public String getAuthenticationId() {
		return this.authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
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

	@Column(name = "user_name")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "client_id")
	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Column(name = "authentication")
	public byte[] getAuthentication() {
		return this.authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	@Column(name = "refresh_token")
	public String getRefreshToken_() {
		return this.refreshToken_;
	}

	public void setRefreshToken_(String refreshToken_) {
		this.refreshToken_ = refreshToken_;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
