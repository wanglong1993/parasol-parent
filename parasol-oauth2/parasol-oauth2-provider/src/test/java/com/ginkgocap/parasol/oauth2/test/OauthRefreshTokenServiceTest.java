package com.ginkgocap.parasol.oauth2.test;

import java.io.Serializable;
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
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.util.StringUtils;

import com.ginkgocap.parasol.oauth2.model.OauthAccessToken;
import com.ginkgocap.parasol.oauth2.model.OauthRefreshToken;
import com.ginkgocap.parasol.oauth2.service.OauthAccessTokenService;
import com.ginkgocap.parasol.oauth2.service.OauthRefreshTokenService;

public class OauthRefreshTokenServiceTest  extends TestBase implements Test  {

	@Resource
	private OauthRefreshTokenService oauthRefreshTokenService;
	@Resource
	private OauthAccessTokenService oauthAccessTokenService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 创建RefreshToken
	 */
	@org.junit.Test
	public void testStoreRefreshToken(){
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
			OAuth2Request oAuth2Request = new OAuth2Request(requestParameters,"unity",authorities,true,scope,resourceIds,redirectUri,responseTypes,extensionProperties);
			OAuth2Authentication authentication =new OAuth2Authentication(oAuth2Request,userAuthentication);
			OauthRefreshToken oauthRefreshToken=setOauthRefreshToken();
			oauthRefreshTokenService.storeRefreshToken(oauthRefreshToken, authentication);
			Assert.assertTrue(oauthRefreshToken.getId()>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void testReadRefreshToken(){
		try {
			OauthRefreshToken oauthRefreshToken=(OauthRefreshToken) oauthRefreshTokenService.readRefreshToken("432143");
			Assert.assertTrue(oauthRefreshToken.getValue().equals("432143"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void testReadAuthenticationForRefreshToken(){
		try {
			OauthRefreshToken oauthRefreshToken=new OauthRefreshToken("432143");
			OAuth2Authentication authentication =oauthRefreshTokenService.readAuthenticationForRefreshToken(oauthRefreshToken);
			Assert.assertTrue(authentication.getName().equals("13677687632"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void testRemoveRefreshToken(){
		try {
			OauthRefreshToken oauthRefreshToken=(OauthRefreshToken) oauthRefreshTokenService.readRefreshToken("432143");
			oauthRefreshTokenService.removeRefreshToken(oauthRefreshToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void testRemoveAccessToken(){
		try {
			OauthAccessToken oauthAccessToken=(OauthAccessToken) oauthRefreshTokenService.readAccessToken("1212312321");
			oauthRefreshTokenService.removeAccessToken(oauthAccessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化RefreshToken对象
	 * @return OauthAccessToken
	 */
	public OauthRefreshToken setOauthRefreshToken(){
		try {
			OauthRefreshToken oauthRefreshToken = new OauthRefreshToken("432143");
			oauthRefreshToken.setCreateTime(new Date());
			return oauthRefreshToken;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
