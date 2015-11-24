package com.ginkgocap.parasol.user.test;

import java.util.Date;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.service.UserInterestIndustryService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

public class UserLoginRegisterServiceTest  extends TestBase implements Test{

	@Resource
	private UserInterestIndustryService userInterestIndustryService;
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 创建登录注册用户
	 */
	@org.junit.Test
	public void testCreateUserLoginRegister(){
		try {
			Long id=userLoginRegisterService.createUserLoginRegister(setUserLoginRegister());
			Assert.assertTrue(id !=null && id > 0L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改密码
	 */
	@org.junit.Test
	public void testUpdatePassword(){
		try {
			boolean bl = userLoginRegisterService.updatePassword(3912310074900481l,"222222");
			Assert.assertTrue(bl==true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据passport获取用户登录注册信息
	 * 根据id获取用户登录注册信息
	 */
	@org.junit.Test
	public void testGetUserLoginRegister(){
		try {
			UserLoginRegister userLoginRegister = userLoginRegisterService.getUserLoginRegister("13677687623");
			UserLoginRegister userLoginRegister2 = userLoginRegisterService.getUserLoginRegister(3912310074900481l);
			Assert.assertTrue(userLoginRegister!=null);
			Assert.assertTrue(userLoginRegister2!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据passport判断用户是否存在
	 * 
	 */
	@org.junit.Test
	public void testPassportIsExist(){
		try {
			boolean bl = userLoginRegisterService.passportIsExist("13677687625");
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据passport获取用户id
	 * 
	 */
	@org.junit.Test
	public void testGetId(){
		try {
			Long id=userLoginRegisterService.getId("13677687625");
			Assert.assertTrue(id!=null && id>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改用户登录IP和登录时间
	 * 
	 */
	@org.junit.Test
	public void testUpdateIpAndLoginTime(){
		try {
			boolean  bl2 =userLoginRegisterService.updateIpAndLoginTime(3912310074900481l, "192.168.101.178");
			Assert.assertTrue(bl2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成salt
	 * Sha256加密密码
	 */
	@org.junit.Test
	public void testSetSalt(){
		try {
			String salt=userLoginRegisterService.setSalt();
			String password=userLoginRegisterService.setSha256Hash(salt, "123456");
			Assert.assertTrue(password!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 真删除登录注册用户
	 * 
	 */
	@org.junit.Test
	public void testRealDeleteUserLoginRegister(){
		try {
			boolean  bl3 =userLoginRegisterService.realDeleteUserLoginRegister(3912310074900481l);
			Assert.assertTrue(bl3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 假删除登录注册用户
	 * 
	 */
	@org.junit.Test
	public void testFakeDeleteUserLoginRegister(){
		try {
			boolean  bl4 =userLoginRegisterService.fakeDeleteUserLoginRegister(3912310074900481l);
			Assert.assertTrue(bl4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 初始化登录注册用户对象
	 * @return userLoginRegister
	 */
	public UserLoginRegister setUserLoginRegister(){
		try {
			UserLoginRegister userLoginRegister = new UserLoginRegister();
			userLoginRegister.setPassport("13677687627");
			byte virtual=1;
			userLoginRegister.setVirtual(virtual);;
			String salt=userLoginRegisterService.setSalt();
			String password=userLoginRegisterService.setSha256Hash(salt, "123456");
			userLoginRegister.setSalt(salt);
			userLoginRegister.setPassword(password);
			userLoginRegister.setSource("app");
			userLoginRegister.setIp("111.111.11.11");
			userLoginRegister.setCtime(new Date());
			userLoginRegister.setUtime(new Date());
			return userLoginRegister;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
