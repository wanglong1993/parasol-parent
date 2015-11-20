package com.ginkgocap.parasol.user.test;

import java.util.Date;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

public class UserLoginRegisterServiceTest  extends TestBase implements Test  {

	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	@org.junit.Test
	public void testCreateUserLoginRegister(){
		UserLoginRegister userLoginRegister = new UserLoginRegister();
		userLoginRegister.setPassport("13677687623");
		userLoginRegister.setPassword("111111");
		byte virtual=1;
		userLoginRegister.setVirtual(virtual);;
		userLoginRegister.setSalt("12121111");
		userLoginRegister.setSource("app");
		userLoginRegister.setIpRegistered("111.111.11.11");
		userLoginRegister.setCtime(new Date());
		userLoginRegister.setUtime(new Date());
		try {
			Long id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
			Assert.assertTrue(id !=null && id > 0L);
		} catch (UserLoginRegisterServiceException e) {
			
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void testGetUserLoginRegister(){
		try {
			UserLoginRegister userLoginRegister = userLoginRegisterService.getUserLoginRegister("13677687623");
			Assert.assertTrue(userLoginRegister!=null);
		} catch (UserLoginRegisterServiceException e) {
			e.printStackTrace();
		}
		
	}
}
