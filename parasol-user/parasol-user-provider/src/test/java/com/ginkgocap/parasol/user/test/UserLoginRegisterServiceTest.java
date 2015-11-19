package com.ginkgocap.parasol.user.test;

import java.util.Date;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.exception.UserServiceException;
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
		userLoginRegister.setPassport("13677687632");
		userLoginRegister.setPassword("111111");
		byte virtual=1;
		userLoginRegister.setVirtual(virtual);;
		userLoginRegister.setSalt("12121111");
		userLoginRegister.setSource("app");
		userLoginRegister.setCtime(new Date());
		userLoginRegister.setUtime(new Date());
		try {
			Long id=userLoginRegisterService.createUserLoginRegister(userLoginRegister);
			Assert.assertTrue(id !=null && id > 0l);
		} catch (UserServiceException e) {
			
			e.printStackTrace();
		}
	}
}
