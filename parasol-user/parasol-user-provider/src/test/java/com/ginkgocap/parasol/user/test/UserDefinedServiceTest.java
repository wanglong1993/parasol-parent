package com.ginkgocap.parasol.user.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.model.UserDefined;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.service.UserDefinedService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

public class UserDefinedServiceTest  extends TestBase implements Test  {

	@Override
	public int countTestCases() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void run(TestResult arg0) {
		// TODO Auto-generated method stub
		
	}

/*	@Resource
	private UserDefinedService userDefinedService;
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	*//**
	 * 添加用户自定义的行业
	 *//*
	@org.junit.Test
	public void testCreateUserInterestIndustry(){
		try {
			Long id=userLoginRegisterService.getId("15966467469");
			List<UserDefined> list =setUserDefinedList(id);
			List<UserDefined> ids =userDefinedService.createUserDefinedByList(list,id);
			for (UserDefined id1 : ids) {
			Assert.assertTrue(id1!=null && id1.getId()>0l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*//**
	 * 根据用户Id获取自定义的id
	 *//*
	@org.junit.Test
	public void testGetIdList(){
		try {
			Long id=userLoginRegisterService.getId("15966467469");
			List<Long> ids = userDefinedService.getIdList(id);
			Assert.assertTrue(ids!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*//**
	 * 根据id列表批量删除用户自定义行业
	 *//*
	@org.junit.Test
	public void testRealDeleteUserInterestIndustryList(){
		try {
			Long id=userLoginRegisterService.getId("15966467469");
			boolean bl = userDefinedService.realDeleteUserDefinedList(userDefinedService.getIdList(id));
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	*//**
	 * 初始化登录注册用户对象
	 * @return userLoginRegister
	 *//*
	public UserLoginRegister setUserLoginRegister(){
		try {
			Long ctime=System.currentTimeMillis();
			UserLoginRegister userLoginRegister = new UserLoginRegister();
			userLoginRegister.setPassport("13677687627");
			byte usetType=1;
			userLoginRegister.setUsetType(usetType);
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
	*//**
	 * 初始化用户自定义对象列表.
	 * @param userId
	 * @param count
	 * @return UserDefined
	 *//*
	public List<UserDefined> setUserDefinedList(Long userId){
		List<UserDefined> list = new ArrayList<UserDefined>();
		Long[] industryIds =new Long[]{1l,2l,3l,4l,5l};
		for (int i = 0; i < industryIds.length; i++) {
			list.add(setUserDefined(userId,industryIds[i]));
		}
		return list;
	}
	*//**
	 * 初始化用户自定义对象.
	 * @param userId
	 * @return UserDefined
	 *//*
	public UserDefined setUserDefined(Long userId,Long industryId){
		Long ctime=System.currentTimeMillis();
		UserDefined userDefined = new  UserDefined();
		userDefined.setUserId(userId);
		userDefined.setUserDefinedModel("自定义模块"+industryId);
		userDefined.setUserDefinedFiled("自定义字段"+industryId);
		userDefined.setUserDefinedValue("自定义值"+industryId);
		userDefined.setCtime(ctime);
		userDefined.setUtime(ctime);
		userDefined.setIp("119.10.29.28");
		return userDefined;
	}
	*/
}
