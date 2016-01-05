package com.ginkgocap.parasol.oauth2.test;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.util.StringUtils;

import com.ginkgocap.parasol.oauth2.model.OauthAccessToken;
import com.ginkgocap.parasol.oauth2.model.OauthRefreshToken;
import com.ginkgocap.parasol.oauth2.service.OauthTokenStoreService;

public class OauthAccessTokenServiceTest  extends TestBase implements Test  {

	@Resource
	private OauthTokenStoreService oauthAccessTokenService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 创建AccessToken
	 */
	@org.junit.Test
	public void testStoreAccessToken(){
		try {
			Map<String, String> requestParameters=new HashMap<String,String>();
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
			grantedAuthorities.add(simpleGrantedAuthority);
			Set<String> scope = StringUtils.commaDelimitedListToSet("getUserInfo,getCode");
			Set<String> resourceIds=StringUtils.commaDelimitedListToSet("ac,cd");
			Set<String> responseTypes=StringUtils.commaDelimitedListToSet("mmmm,nnnn");
			Collection<? extends GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			Map<String, Serializable> extensionProperties = new HashMap<String,Serializable>();
			for (GrantedAuthority grantedAuthority : grantedAuthorities) {
//				authorities.add(grantedAuthority);
			}
			String redirectUri ="http://www.baidu.com/index.html";
			UsernamePasswordAuthenticationToken userAuthentication =new   UsernamePasswordAuthenticationToken("13677687632","123456"); 
			OAuth2Request oAuth2Request = new OAuth2Request(requestParameters,"unity5",authorities,true,scope,resourceIds,redirectUri,responseTypes,extensionProperties);
			OAuth2Authentication authentication =new OAuth2Authentication(oAuth2Request,userAuthentication);
			OauthAccessToken oauthAccessToken=setOauthAccessToken();
			OauthRefreshToken oauthRefreshToken=new OauthRefreshToken("fretrtrtdsa");
			oauthAccessToken.setRefreshToken(oauthRefreshToken);
			oauthAccessTokenService.storeAccessToken(oauthAccessToken, authentication);
			Assert.assertTrue(oauthAccessToken.getId()>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void testReadAccessToken(){
		try {
			OauthAccessToken oauthAccessToken=(OauthAccessToken) oauthAccessTokenService.readAccessToken("1212312321");
			Assert.assertTrue(oauthAccessToken.getId()>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	@org.junit.Test
	public void testReadAuthentication(){
		try {
			OauthAccessToken oauthAccessToken=(OauthAccessToken) oauthAccessTokenService.readAccessToken("1212312321");
			OAuth2Authentication authentication=oauthAccessTokenService.readAuthentication(oauthAccessToken);
			Assert.assertTrue(authentication.getName().equals("13677687632"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	@org.junit.Test
	public void testGetAccessToken(){
		try {
			OauthAccessToken oauthAccessToken=(OauthAccessToken) oauthAccessTokenService.readAccessToken("1212312321");
			OAuth2Authentication authentication=oauthAccessTokenService.readAuthentication(oauthAccessToken);
			oauthAccessToken=(OauthAccessToken)oauthAccessTokenService.getAccessToken(authentication);
			Assert.assertTrue(oauthAccessToken.getUserName().equals("343lry11"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	@org.junit.Test
	public void testFindTokensByClientIdAndUserName(){
		try {
			Collection<OAuth2AccessToken> collection=oauthAccessTokenService.findTokensByClientIdAndUserName("fdsaf33r4r", "343lry11");
			for (OAuth2AccessToken oAuth2AccessToken : collection) {
				OauthAccessToken oauthAccessToken=(OauthAccessToken)oAuth2AccessToken;
				Assert.assertTrue(oauthAccessToken.getClientId().equals("fdsaf33r4r"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	@org.junit.Test
	public void testFindTokensByClientId(){
		try {
			Collection<OAuth2AccessToken> collection=oauthAccessTokenService.findTokensByClientId("fdsaf33r4r");
			for (OAuth2AccessToken oAuth2AccessToken : collection) {
				OauthAccessToken oauthAccessToken=(OauthAccessToken)oAuth2AccessToken;
				Assert.assertTrue(oauthAccessToken.getClientId().equals("fdsaf33r4r"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	@org.junit.Test
	public void testRemoveAccessTokenUsingRefreshToken(){
		try {
			OauthRefreshToken oauthRefreshToken=new OauthRefreshToken("fretrtrtdsa");
			oauthAccessTokenService.removeAccessTokenUsingRefreshToken(oauthRefreshToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	@org.junit.Test
	public void testRemoveAccessToken(){
		try {
			OauthAccessToken oauthAccessToken=(OauthAccessToken) oauthAccessTokenService.readAccessToken("1212312321");
			oauthAccessTokenService.removeAccessToken(oauthAccessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void testRemoveRefreshToken(){
		try {
			OauthRefreshToken oauthRefreshToken=(OauthRefreshToken) oauthAccessTokenService.readRefreshToken("432143");
			oauthAccessTokenService.removeRefreshToken(oauthRefreshToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 初始化AccessToken对象
	 * @return OauthAccessToken
	 */
	public OauthAccessToken setOauthAccessToken(){
		try {
			OauthAccessToken oauthAccessToken = new OauthAccessToken();
			oauthAccessToken.setAuthenticationId("uytruytru");
			oauthAccessToken.setUserName("13677687632");
			oauthAccessToken.setClientId("fdsaf33r4r");
			oauthAccessToken.setRefreshToken_("refre334123");
			oauthAccessToken.setCreateTime(new Date());
			oauthAccessToken.setValue("1212312321");
			return oauthAccessToken;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
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
}
