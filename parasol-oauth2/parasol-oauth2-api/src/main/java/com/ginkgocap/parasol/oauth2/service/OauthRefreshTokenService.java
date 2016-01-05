package com.ginkgocap.parasol.oauth2.service;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;


/**
 * 操作RefreshToken接口定义
 * 
 */
public interface OauthRefreshTokenService  {
	public void storeRefreshToken(OAuth2RefreshToken refreshToken,OAuth2Authentication authentication) ;
	
	public OAuth2RefreshToken readRefreshToken(String tokenValue) ;
	
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token);
	
	public void removeRefreshToken(OAuth2RefreshToken token);

}
