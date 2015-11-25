package com.ginkgocap.parasol.user.test;

import java.io.Serializable;
import java.util.ArrayList;
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
			Long id=userLoginRegisterService.getId("13677687623");
			List<UserInterestIndustry> list =setUserInterestIndustryList(id);
//			List<Serializable> ids =userInterestIndustryService.createUserInterestIndustryByList(list,id);
//			for (Serializable id1 : ids) {
//				UserInterestIndustry entity=(UserInterestIndustry)id1;
//				Assert.assertTrue(entity!=null && entity.getId()>0l);
//			}
			List<UserInterestIndustry> ids =userInterestIndustryService.createUserInterestIndustryByList(list,id);
			for (UserInterestIndustry id1 : ids) {
			Assert.assertTrue(id1!=null && id1.getId()>0l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据行业id获取userId分页列表
	 */
	@org.junit.Test
	public void testGetUserIdListByIndustryId(){
		try {
			List<Long> list =userInterestIndustryService.getUserIdListByIndustryId(1, 2, 2l);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据用户Id获取感兴趣的id
	 */
	@org.junit.Test
	public void testGetIdList(){
		try {
			Long id=userLoginRegisterService.getId("13677687623");
			List<Long> ids = userInterestIndustryService.getIdList(id);
			Assert.assertTrue(ids!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id列表批量删除用户感兴趣行业
	 */
	@org.junit.Test
	public void testRealDeleteUserInterestIndustryList(){
		try {
			Long id=userLoginRegisterService.getId("13677687623");
			boolean bl = userInterestIndustryService.realDeleteUserInterestIndustryList(userInterestIndustryService.getIdList(id));
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
	 * 初始化用户感兴趣的对象列表.
	 * @param userId
	 * @param count
	 * @return userInterestIndustry
	 */
	public List<UserInterestIndustry> setUserInterestIndustryList(Long userId){
		List<UserInterestIndustry> list = new ArrayList<UserInterestIndustry>();
		Long[] industryIds =new Long[]{1l,2l,3l,4l,5l};
		for (int i = 0; i < industryIds.length; i++) {
			list.add(setUserInterestIndustry(userId,industryIds[i]));
		}
		return list;
	}
	/**
	 * 初始化用户感兴趣的对象.
	 * @param userId
	 * @return userInterestIndustry
	 */
	public UserInterestIndustry setUserInterestIndustry(Long userId,Long industryId){
		Long ctime=System.currentTimeMillis();
		UserInterestIndustry userInterestIndustry = new  UserInterestIndustry();
		userInterestIndustry.setUserId(userId);
		userInterestIndustry.setFirstIndustryId(industryId);
		userInterestIndustry.setCtime(ctime);
		userInterestIndustry.setUtime(ctime);
		userInterestIndustry.setIp("119.10.29.28");
		return userInterestIndustry;
	}
	
}
