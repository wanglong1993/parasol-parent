package com.ginkgocap.parasol.oauth2.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.oauth2.model.OauthAccessToken;
import com.ginkgocap.parasol.oauth2.model.OauthClientDetails;
import com.ginkgocap.parasol.oauth2.service.OauthClientDetailsService;
import com.ginkgocap.parasol.oauth2.service.OauthRefreshTokenService;
import com.ginkgocap.parasol.oauth2.service.OauthTokenStoreService;

@Service("oauthTokenStoreService")
public class OauthTokenStoreServiceImpl extends BaseService<OauthAccessToken> implements OauthTokenStoreService {

	private static Logger logger = Logger.getLogger(OauthTokenStoreServiceImpl.class);
	
	@Autowired
	private OauthRefreshTokenService oauthRefreshTokenService;
	@Autowired
	private OauthClientDetailsService oauthClientDetailsService;
	
	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();  
	private static final String OauthAccessToken_List_By_tokenId = "OauthAccessToken_List_By_tokenId"; 
	private static final String OauthAccessToken_List_By_refreshToken = "OauthAccessToken_List_By_refreshToken"; 
	private static final String OauthAccessToken_List_By_Authentication_Id = "OauthAccessToken_List_By_Authentication_Id"; 
	private static final String OauthAccessToken_List_By_UserName_clientId = "OauthAccessToken_List_By_UserName_clientId"; 
	private static final String OauthAccessToken_List_By_ClientId = "OauthAccessToken_List_By_ClientId"; 

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
		return readAuthentication(token.getValue());
	}
	
	@Override
	public OAuth2Authentication readAuthentication(String token) {
		if(StringUtils.isBlank(token))return null;
		String tokenId=extractTokenKey(token);
		Long id;
		OauthAccessToken oauthAccessToken;
		try {
			id = (Long)getMapId(OauthAccessToken_List_By_tokenId,tokenId);
			oauthAccessToken=getEntity(id);
			if(ObjectUtils.isEmpty(oauthAccessToken)) return null; 
		} catch (BaseServiceException e1) {
			e1.printStackTrace();
			return null;
		}
		OAuth2Authentication authentication = null;  
		OAuth2Request storedRequest = null;
		boolean isChange=false;
        try {
            authentication = SerializationUtils.deserialize(oauthAccessToken.getAuthentication());  
            if(ObjectUtils.isEmpty(authentication))return null;
            OauthClientDetails oauthClientDetails =(OauthClientDetails) oauthClientDetailsService.loadClientByClientId(authentication.getOAuth2Request().getClientId());
            if(ObjectUtils.isEmpty(oauthClientDetails))return null;
            Set<String> resourceIds= oauthClientDetails.getResourceIds();
            Set<String> scopes= oauthClientDetails.getScope();
            OAuth2Request oauth2Request=authentication.getOAuth2Request();
            if(!scopes.equals(oauth2Request.getScope()) && !resourceIds.equals(oauth2Request.getResourceIds())){
            	storedRequest = new OAuth2Request(oauth2Request.getRequestParameters(),oauth2Request.getClientId(),oauth2Request.getAuthorities(),oauth2Request.isApproved(),scopes,resourceIds,oauth2Request.getRedirectUri(),oauth2Request.getResponseTypes(),oauth2Request.getExtensions());
            	isChange=true;
            }
            if(!isChange){
	            if(!scopes.equals(oauth2Request.getScope())){
	            	storedRequest = new OAuth2Request(oauth2Request.getRequestParameters(),oauth2Request.getClientId(),oauth2Request.getAuthorities(),oauth2Request.isApproved(),scopes,oauth2Request.getResourceIds(),oauth2Request.getRedirectUri(),oauth2Request.getResponseTypes(),oauth2Request.getExtensions());
	            	isChange=true;
	            }
            }
            if(!isChange){
	            if(!resourceIds.equals(oauth2Request.getResourceIds())){
	            	storedRequest = new OAuth2Request(oauth2Request.getRequestParameters(),oauth2Request.getClientId(),oauth2Request.getAuthorities(),oauth2Request.isApproved(),oauth2Request.getScope(),resourceIds,oauth2Request.getRedirectUri(),oauth2Request.getResponseTypes(),oauth2Request.getExtensions());
	            	isChange=true;
	            }
            }
            if(isChange){
            	authentication =new OAuth2Authentication(storedRequest,authentication.getUserAuthentication());
            	removeAccessToken(oauthAccessToken);
            	storeAccessToken(oauthAccessToken,authentication);
            	OAuth2RefreshToken oAuth2RefreshToken=oauthRefreshTokenService.getRefreshToken(oauthAccessToken.getRefreshToken_());
            	removeRefreshToken(oAuth2RefreshToken);
            	storeRefreshToken(oAuth2RefreshToken, authentication);
            }
        }  
        catch (EmptyResultDataAccessException e) {  
            if (logger.isDebugEnabled()) {  
            	logger.info("Failed to find access token for token " + token);  
            }  
        }catch (IllegalArgumentException e) {
        	logger.warn("Failed to deserialize authentication for " + token, e);
			removeAccessToken(oauthAccessToken);
		}
        return authentication;  
	}
	
	public OAuth2Authentication getAuthentication(String tokenId) {
		if(StringUtils.isBlank(tokenId))return null;
		Long id;
		OauthAccessToken oauthAccessToken;
		try {
			id = (Long)getMapId(OauthAccessToken_List_By_tokenId,tokenId);
			oauthAccessToken=getEntity(id);
		} catch (BaseServiceException e1) {
			e1.printStackTrace();
			return null;
		}
		OAuth2Authentication authentication = null;  
		OAuth2Request storedRequest = null;
		OAuth2AccessToken oAuth2AccessToken=null;
		boolean isChange=false;
        try {  
            authentication = SerializationUtils.deserialize(oauthAccessToken.getAuthentication());  
            OauthClientDetails oauthClientDetails =(OauthClientDetails) oauthClientDetailsService.loadClientByClientId(authentication.getOAuth2Request().getClientId());
            Set<String> resourceIds= oauthClientDetails.getResourceIds();
            Set<String> scopes= oauthClientDetails.getScope();
            OAuth2Request oauth2Request=authentication.getOAuth2Request();
            if(!scopes.equals(oauth2Request.getScope()) && !resourceIds.equals(oauth2Request.getResourceIds())){
            	storedRequest = new OAuth2Request(oauth2Request.getRequestParameters(),oauth2Request.getClientId(),oauth2Request.getAuthorities(),oauth2Request.isApproved(),scopes,resourceIds,oauth2Request.getRedirectUri(),oauth2Request.getResponseTypes(),oauth2Request.getExtensions());
            	isChange=true;
            }
            if(!isChange){
	            if(!scopes.equals(oauth2Request.getScope())){
	            	storedRequest = new OAuth2Request(oauth2Request.getRequestParameters(),oauth2Request.getClientId(),oauth2Request.getAuthorities(),oauth2Request.isApproved(),scopes,oauth2Request.getResourceIds(),oauth2Request.getRedirectUri(),oauth2Request.getResponseTypes(),oauth2Request.getExtensions());
	            	isChange=true;
	            }
            }
            if(!isChange){
	            if(!resourceIds.equals(oauth2Request.getResourceIds())){
	            	storedRequest = new OAuth2Request(oauth2Request.getRequestParameters(),oauth2Request.getClientId(),oauth2Request.getAuthorities(),oauth2Request.isApproved(),oauth2Request.getScope(),resourceIds,oauth2Request.getRedirectUri(),oauth2Request.getResponseTypes(),oauth2Request.getExtensions());
	            	isChange=true;
	            }
            }
            if(isChange){
            	authentication =new OAuth2Authentication(storedRequest,authentication.getUserAuthentication());
            	removeAccessToken(oauthAccessToken);
            	storeAccessToken(oauthAccessToken,authentication);
            	OAuth2RefreshToken oAuth2RefreshToken=oauthRefreshTokenService.getRefreshToken(oauthAccessToken.getRefreshToken_());
            	removeRefreshToken(oAuth2RefreshToken);
            	storeRefreshToken(oAuth2RefreshToken, authentication);
            }
        }  
        catch (EmptyResultDataAccessException e) {  
            if (logger.isDebugEnabled()) {  
            	logger.info("Failed to find access token for token " + tokenId);  
            }  
        }catch (IllegalArgumentException e) {
        	logger.warn("Failed to deserialize authentication for " + tokenId, e);
        	oAuth2AccessToken=SerializationUtils.deserialize(oauthAccessToken.getToken());
			removeAccessToken(oAuth2AccessToken);
		}
        return authentication;  
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token,OAuth2Authentication authentication) {
		if(!ObjectUtils.isEmpty(token) && !ObjectUtils.isEmpty(authentication)){
			String refreshToken = null;  
			OauthAccessToken oauthAccessToken=new OauthAccessToken();
			OauthAccessToken token2=null;
	        if (token.getRefreshToken() != null) {  
	            refreshToken = token.getRefreshToken().getValue();  
	        }
	        
		        boolean isExist = false;
				if (readAccessToken(token.getValue()) != null) {
					//removeAccessToken(token);
					isExist = true;
				}
		        if(token instanceof OauthAccessToken){
		        	token2=((OauthAccessToken)token);
			        oauthAccessToken.setTokenId(token2.getTokenId());
			        oauthAccessToken.setToken(token2.getToken());
			        oauthAccessToken.setRefreshToken_(token2.getRefreshToken_());
			        oauthAccessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
			        oauthAccessToken.setAuthentication(SerializationUtils.serialize(authentication));
			        oauthAccessToken.setClientId(authentication.getOAuth2Request().getClientId());
			        oauthAccessToken.setUserName(authentication.getName());
			        oauthAccessToken.setCreateTime(new Date());
		        }else{
			        oauthAccessToken.setTokenId(extractTokenKey(token.getValue()));
			        oauthAccessToken.setToken(SerializationUtils.serialize(token));
			        oauthAccessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
			        oauthAccessToken.setAuthentication(SerializationUtils.serialize(authentication));
			        oauthAccessToken.setRefreshToken_(extractTokenKey(refreshToken));
			        oauthAccessToken.setClientId(authentication.getOAuth2Request().getClientId());
			        oauthAccessToken.setUserName(authentication.getName());
			        oauthAccessToken.setCreateTime(new Date());
		        }
		        try {
		        	if (isExist) {
						//synchronized (this) {
							updateEntity(oauthAccessToken);
						//}
						} else {
							saveEntity(oauthAccessToken);
						}
				} catch (BaseServiceException e) {
					e.printStackTrace();
					return;
				}
		}else return ;
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		if(StringUtils.isBlank(tokenValue))return null;
		String tokenId=extractTokenKey(tokenValue);
		Long id;
		OauthAccessToken oauthAccessToken = null;
		OAuth2AccessToken  oAuth2AccessToken=null;
		try {
			id = (Long)getMapId(OauthAccessToken_List_By_tokenId,tokenId);
			if(id==null || id<=0l) return null;
			oauthAccessToken=getEntity(id);
			if(ObjectUtils.isEmpty(oauthAccessToken))return null;
			oAuth2AccessToken=SerializationUtils.deserialize(oauthAccessToken.getToken());
			return oAuth2AccessToken;
		} catch (BaseServiceException e1) {
			e1.printStackTrace();
			return null;
		}catch (IllegalArgumentException e) {
			logger.warn("Failed to deserialize access token for " + tokenValue, e);
			removeAccessToken(oAuth2AccessToken);
		}
		return oAuth2AccessToken;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		if(ObjectUtils.isEmpty(token))return ;
		String tokenId= extractTokenKey(token.getValue());
		if(StringUtils.isEmpty(tokenId)){
			if( token instanceof OauthAccessToken){
				try {
					deleteEntity(((OauthAccessToken)token).getId());
					return;
				} catch (BaseServiceException e) {
					e.printStackTrace();
					return;
				}
			}
		}
		try {
			Long id = (Long)getMapId(OauthAccessToken_List_By_tokenId,tokenId);
			deleteEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}	
	}
	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken,OAuth2Authentication authentication) {
		oauthRefreshTokenService.storeRefreshToken(refreshToken, authentication);
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		return oauthRefreshTokenService.readRefreshToken(tokenValue);
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		return oauthRefreshTokenService.readAuthenticationForRefreshToken(token);
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		oauthRefreshTokenService.removeRefreshToken(token);
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		if(ObjectUtils.isEmpty(refreshToken))return ;
		String refresh_token=extractTokenKey(refreshToken.getValue());
		try {
			Long id = (Long)getMapId(OauthAccessToken_List_By_refreshToken,refresh_token);
			deleteEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}	
		
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		if(ObjectUtils.isEmpty(authentication))return null;
		String authenticationId = authenticationKeyGenerator.extractKey(authentication);
		OauthAccessToken oauthAccessToken=null;
		OAuth2AccessToken oAuth2AccessToken=null;
		try {
			Long id = (Long)getMapId(OauthAccessToken_List_By_Authentication_Id,authenticationId);
			if(id==null || id <=0l) return null;
			oauthAccessToken=getEntity(id);
			if(ObjectUtils.isEmpty(oauthAccessToken))return null;
			oAuth2AccessToken=SerializationUtils.deserialize(oauthAccessToken.getToken());
			OAuth2Authentication oAuth2Authentication=readAuthentication(oAuth2AccessToken.getValue());
			if(ObjectUtils.isEmpty(oAuth2Authentication))return null;
			if (oauthAccessToken != null && !authenticationId.equals(authenticationKeyGenerator.extractKey(oAuth2Authentication))) {
				removeAccessToken(oAuth2AccessToken);
				// Keep the store consistent (maybe the same user is represented by this authentication but the details have
				// changed)
				storeAccessToken(oauthAccessToken, authentication);
			}
			return SerializationUtils.deserialize(oauthAccessToken.getToken());
		} catch (BaseServiceException e) {
			e.printStackTrace();
			return  null;
		}catch (IllegalArgumentException e) {
			logger.error("Could not extract access token for authentication " + authentication, e);
		}
		return oauthAccessToken;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		if(StringUtils.isEmpty(clientId))return Collections.EMPTY_SET;
		if(StringUtils.isEmpty(userName))return Collections.EMPTY_SET;
		List<OauthAccessToken> accessTokens = new ArrayList<OauthAccessToken>();
		Collection<OAuth2AccessToken> collection = new HashSet<OAuth2AccessToken>();
		try {
			List<Long> ids =getIds(OauthAccessToken_List_By_UserName_clientId,userName,clientId);
			if(ids==null || ids.size()==0) return Collections.EMPTY_SET;
			accessTokens=getEntityByIds(ids);
			if(ObjectUtils.isEmpty(accessTokens))return Collections.EMPTY_SET;
			for (OauthAccessToken oauthAccessToken : accessTokens) {
				if(oauthAccessToken!=null){
					OAuth2AccessToken oAuth2AccessToken =SerializationUtils.deserialize(oauthAccessToken.getToken());
					collection.add(oAuth2AccessToken);
				}
			}
			return collection;
		} catch (BaseServiceException e) {
			e.printStackTrace();
			return  null;
		}
	}
	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		if(StringUtils.isEmpty(clientId))return Collections.EMPTY_SET;
		List<OauthAccessToken> accessTokens = new ArrayList<OauthAccessToken>();
		Collection<OAuth2AccessToken> collection = new HashSet<OAuth2AccessToken>();
		try {
			List<Long> ids =getIds(OauthAccessToken_List_By_ClientId,clientId);
			if(ids==null || ids.size()==0) return Collections.EMPTY_SET;
			accessTokens=getEntityByIds(ids);
			if(ObjectUtils.isEmpty(accessTokens))return Collections.EMPTY_SET;
			for (OauthAccessToken oauthAccessToken : accessTokens) {
				if(oauthAccessToken!=null){
					OAuth2AccessToken oAuth2AccessToken =SerializationUtils.deserialize(oauthAccessToken.getToken());
					collection.add(oAuth2AccessToken);
				}
			}
			return collection;
		} catch (BaseServiceException e) {
			e.printStackTrace();
			return  null;
		}
	}

}
