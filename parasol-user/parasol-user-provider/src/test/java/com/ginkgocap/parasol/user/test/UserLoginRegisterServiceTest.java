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
		userLoginRegister.setIp("111.111.11.11");
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
	public void testUpdatePassword(){
		try {
			boolean bl = userLoginRegisterService.updatePassword(3912310074900481l,"222222");
			Assert.assertTrue(bl==true);
		} catch (UserLoginRegisterServiceException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 测试查询方法
	 */
	@org.junit.Test
	public void testGetUserLoginRegister(){
		try {
			UserLoginRegister userLoginRegister = userLoginRegisterService.getUserLoginRegister("13677687623");
			UserLoginRegister userLoginRegister2 = userLoginRegisterService.getUserLoginRegister(3912310074900481l);
			boolean bl = userLoginRegisterService.passportIsExist("13677687625");
			boolean  bl2 =userLoginRegisterService.updateIpAndLoginTime(3912310074900481l, "192.168.101.178");
			boolean  bl3 =userLoginRegisterService.realDeleteUserLoginRegister(3912310074900481l);
			boolean  bl4 =userLoginRegisterService.fakeDeleteUserLoginRegister(3912310074900481l);
			Long id=userLoginRegisterService.getId("13677687625");
			boolean bl5=userLoginRegisterService.realDeleteUserLoginRegister(id);
			String salt=userLoginRegisterService.setSalt();
			String password=userLoginRegisterService.setSha256Hash(salt, "123456");
			Assert.assertTrue(userLoginRegister!=null);
			Assert.assertTrue(userLoginRegister2!=null);
			Assert.assertTrue(bl);
			Assert.assertTrue(bl2);
			Assert.assertTrue(bl3);
			Assert.assertTrue(bl4);
			Assert.assertTrue(bl5);
			Assert.assertTrue(password!=null);
		} catch (UserLoginRegisterServiceException e) {
			e.printStackTrace();
		}
	}
	
	
}
