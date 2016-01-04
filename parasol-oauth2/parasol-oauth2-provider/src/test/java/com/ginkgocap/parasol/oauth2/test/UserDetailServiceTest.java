package com.ginkgocap.parasol.oauth2.test;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.oauth2.model.SecurityUserDetails;
import com.ginkgocap.parasol.oauth2.service.UserDetailService;

public class UserDetailServiceTest  extends TestBase implements Test  {

	@Resource
	private UserDetailService userDetailService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	
	/**
	 * 根据用户clientId获取客户端信息
	 */
	@org.junit.Test
	public void testLoadClientByClientId(){
		try {
			SecurityUserDetails securityUserDetails=(SecurityUserDetails) userDetailService.loadUserByUsername("13677687633");
			Assert.assertTrue(securityUserDetails.user().getPassport()!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据用户clientId获取客户端信息
	 */
	@org.junit.Test
	public void testloadClientByClientId(){
		try {
			SecurityUserDetails securityUserDetails=(SecurityUserDetails) userDetailService.loadUserByUsername("13677687633");
			Assert.assertTrue(securityUserDetails.user().getPassport()!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
