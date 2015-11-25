package com.ginkgocap.parasol.user.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.model.UserInterestIndustry;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.service.UserInterestIndustryService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

public class UserInterestIndustryServiceTest  extends TestBase implements Test  {

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
	 * 添加用户感兴趣的行业
	 */
	@org.junit.Test
	public void testCreateUserInterestIndustry(){
		try {
			Long id=userLoginRegisterService.getId("13677687622");
			Long id2=userInterestIndustryService.createUserInterestIndustry(setUserInterestIndustry(id));
			Assert.assertTrue(id !=null && id > 0L);
			Assert.assertTrue(id2 !=null && id2 > 0L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改感兴趣行业
	 */
	@org.junit.Test
	public void testUpdateUserInterestIndustry(){
		try {
			boolean bl=userInterestIndustryService.updateUserInterestIndustry(3912415138021384l, 1l, "192.168.101.178");
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据userId获取id
	 */
	@org.junit.Test
	public void testGetId(){
		try {
			Long id=userLoginRegisterService.getId("13677687626");
			Long id2=userInterestIndustryService.getId(id);
			Assert.assertTrue(id2!=null && id2>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据行业id获取userId分页列表
	 */
	@org.junit.Test
	public void testGetUserIdList(){
		try {
			List<Long> list =userInterestIndustryService.getUserIdList(1, 2, 1l, null, null, 1);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 用户Id是否存在
	 */
	@org.junit.Test
	public void testUserIdExists(){
		try {
			boolean bl = userInterestIndustryService.userIdExists(3912310074900481l);
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id列表批量删除登录注册用户
	 */
	@org.junit.Test
	public void testRealDeleteUserInterestIndustryList(){
		try {
			List<Serializable> list = new ArrayList<Serializable>();
			list.add(3912415834275843l);
			list.add(3912417767849991l);
			boolean bl = userInterestIndustryService.realDeleteUserInterestIndustryList(list);
			Assert.assertTrue(bl);
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
			Long ctime=System.currentTimeMillis();
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
			userLoginRegister.setCtime(ctime);
			userLoginRegister.setUtime(ctime);
			return userLoginRegister;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 初始化用户感兴趣的对象.
	 * @param userId
	 * @return userInterestIndustry
	 */
	public UserInterestIndustry setUserInterestIndustry(Long userId){
		Long ctime=System.currentTimeMillis();
		UserInterestIndustry userInterestIndustry = new  UserInterestIndustry();
		userInterestIndustry.setUserId(userId);
		userInterestIndustry.setFirstIndustryId(1l);
		userInterestIndustry.setCtime(ctime);
		userInterestIndustry.setUtime(ctime);
		userInterestIndustry.setIp("119.10.29.28");
		return userInterestIndustry;
	}
	
}
