package com.ginkgocap.parasol.oauth2.test;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.oauth2.model.OauthClientDetails;
import com.ginkgocap.parasol.oauth2.service.OauthClientDetailsService;

public class OauthClientDetailServiceTest  extends TestBase implements Test  {

	@Resource
	private OauthClientDetailsService oauthClientDetailsService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	
	/**
	 * 根据用户clientId获取客户端用户信息
	 */
	@org.junit.Test
	public void testLoadUserByUsername(){
		try {
			OauthClientDetails oauthClientDetails=(OauthClientDetails) oauthClientDetailsService.loadClientByClientId("unity-client");
			Assert.assertTrue(oauthClientDetails.getClientSecret()!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建客户端信息
	 */
	@org.junit.Test
	public void testAddClientDetails(){
		try {
			OauthClientDetails oauthClientDetails=setOauthClientDetails();
			oauthClientDetailsService.addClientDetails(oauthClientDetails);
			Assert.assertTrue(oauthClientDetails.getId()>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改客户端信息
	 */
	@org.junit.Test
	public void testUpdateClientDetails(){
		try {
			OauthClientDetails oauthClientDetails=(OauthClientDetails) oauthClientDetailsService.loadClientByClientId("7992116517");
			oauthClientDetails.setScope_("getIdentifyingCode,getUserInfo");
			oauthClientDetailsService.updateClientDetails(oauthClientDetails);
			Assert.assertTrue(oauthClientDetails.getId()>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改客户端信息
	 */
	@org.junit.Test
	public void testUpdateClientSecret(){
		try {
			OauthClientDetails oauthClientDetails=(OauthClientDetails) oauthClientDetailsService.loadClientByClientId("7992116517");
			oauthClientDetailsService.updateClientSecret("7992116517","qwerytyu");
			Assert.assertTrue(oauthClientDetails.getId()>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取客户端信息列表
	 */
	@org.junit.Test
	public void testListClientDetails(){
		try {
			List<OauthClientDetails> list=oauthClientDetailsService.listClientDetails(0,2);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化登录注册用户对象
	 * @return oauthClientDetails
	 */
	public OauthClientDetails setOauthClientDetails(){
		try {
			Long ctime=System.currentTimeMillis();
			OauthClientDetails oauthClientDetails = new OauthClientDetails();
			oauthClientDetails.setCompanyName("北京金桐网投资有限公司");
			oauthClientDetails.setApplicationName("社群1");
			oauthClientDetails.setScope_("getIdentifyingCode");
			oauthClientDetails.setIp("192.168.101.1");
			oauthClientDetails.setResourceIds_("unity-resource");
			oauthClientDetails.setAuthorities_("role_client");
			oauthClientDetails.setRegisteredRedirectUris_("http://localhost:8080/inde.html");
			oauthClientDetails.setAuthorizedGrantTypes_("authorization_code,refresh_token,implicit");
			return oauthClientDetails;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
