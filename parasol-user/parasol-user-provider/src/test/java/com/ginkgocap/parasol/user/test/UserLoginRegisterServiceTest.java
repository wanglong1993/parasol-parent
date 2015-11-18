package com.ginkgocap.parasol.user.test;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

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
		try {
			userLoginRegisterService.createUserLoginRegister(userLoginRegister);
		} catch (UserServiceException e) {
			
			e.printStackTrace();
		}
	}
}
