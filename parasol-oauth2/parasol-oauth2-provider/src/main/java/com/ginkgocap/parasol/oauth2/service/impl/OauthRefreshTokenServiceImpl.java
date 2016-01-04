package com.ginkgocap.parasol.oauth2.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.oauth2.model.OauthRefreshToken;
import com.ginkgocap.parasol.oauth2.service.OauthAccessTokenService;
import com.ginkgocap.parasol.oauth2.service.OauthRefreshTokenService;

@Service("oauthRefreshTokenService")
public class OauthRefreshTokenServiceImpl extends BaseService<OauthRefreshToken> implements OauthRefreshTokenService {

	private static Logger logger = Logger.getLogger(OauthRefreshTokenServiceImpl.class);
	@Autowired
	private OauthAccessTokenService oauthAccessTokenService;
	private static final String OauthRefreshToken_List_By_tokenId = "OauthRefreshToken_List_By_tokenId"; 

	protected String extractTokenKey(String value) {
		if (value == null) {
			return null;
		}
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(value.getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}
	
	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return oauthAccessTokenService.readAuthentication(token);
	}
	
	@Override
	public OAuth2Authentication readAuthentication(String token) {
		return oauthAccessTokenService.readAuthentication(token);
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token,OAuth2Authentication authentication) {
		oauthAccessTokenService.storeAccessToken(token, authentication);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		return oauthAccessTokenService.readAccessToken(tokenValue);
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		oauthAccessTokenService.removeAccessToken(token);
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken,OAuth2Authentication authentication) {
		OauthRefreshToken oauthRefreshToken=(OauthRefreshToken) refreshToken;  
        oauthRefreshToken.setTokenId(extractTokenKey(refreshToken.getValue()));  
        oauthRefreshToken.setAuthentication(SerializationUtils.serialize(authentication));  
        oauthRefreshToken.setToken(SerializationUtils.serialize(oauthRefreshToken));  
        try {
			saveEntity(oauthRefreshToken);
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		if(StringUtils.isBlank(tokenValue))return null;
		String tokenId=extractTokenKey(tokenValue);
		Long id;
		OauthRefreshToken oauthRefreshToken;
		try {
			id = (Long)getMapId(OauthRefreshToken_List_By_tokenId,tokenId);
			if(id==null || id<=0l) return null;
			oauthRefreshToken=getEntity(id);
			if(ObjectUtils.isEmpty(oauthRefreshToken))return null;
			oauthRefreshToken=SerializationUtils.deserialize(oauthRefreshToken.getToken());
			return oauthRefreshToken;
		} catch (BaseServiceException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		if(ObjectUtils.isEmpty(token))return null;
		String tokenId=extractTokenKey(token.getValue());
		Long id;
		OauthRefreshToken oauthRefreshToken;
		try {
			id = (Long)getMapId(OauthRefreshToken_List_By_tokenId,tokenId);
			if(id==null || id<=0l) return null;
			oauthRefreshToken=getEntity(id);
			if(ObjectUtils.isEmpty(oauthRefreshToken))return null;
			return SerializationUtils.deserialize(oauthRefreshToken.getAuthentication());
		} catch (BaseServiceException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		if(ObjectUtils.isEmpty(token))return ;
		String tokenId=extractTokenKey(token.getValue());
		Long id;
		try {
			id = (Long)getMapId(OauthRefreshToken_List_By_tokenId,tokenId);
			if(id==null || id<=0l) return ;
			deleteEntity(id);
		} catch (BaseServiceException e1) {
			e1.printStackTrace();
			return ;
		}
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		oauthAccessTokenService.removeAccessTokenUsingRefreshToken(refreshToken);
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		return oauthAccessTokenService.getAccessToken(authentication);
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		return oauthAccessTokenService.findTokensByClientIdAndUserName(clientId, userName);
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		return oauthAccessTokenService.findTokensByClientId(clientId);
	}

}
