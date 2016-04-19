package com.ginkgocap.parasol.user.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.model.UserInfo;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserInfoService;

public class UserInfoServiceTest  extends TestBase implements Test  {

	@Resource
	private UserInfoService userInfoService;
	@Resource
	private UserBasicService userBasicService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 创建用户个人信息
	 */
	@org.junit.Test
	public void testCreateUserInfo(){
		try {
			Long id2 =userInfoService.createUserInfo(setUserInfo(3953254564364289l));
			Assert.assertTrue(id2!=null && id2>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改用户个人信息
	 */
	@org.junit.Test
	public void testUpdateUserInfo(){
		try {
			UserInfo userInfo = userInfoService.getUserInfo(3953254564364289l);
			boolean bl =userInfoService.updateUserInfo(userInfo);
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id获取用户个人信息
	 */
	@org.junit.Test
	public void testGetUserInfo(){
		try {
			UserInfo list = userInfoService.getUserInfo(3953254564364289l);
			Assert.assertTrue(list==null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据userId列表获取用户个人信息列表
	 */
	@org.junit.Test
	public void testGetUserInfoList(){
		try {
			List<Long> ids=new ArrayList<Long>();
			ids.add(3953254564364289l);
			List<UserInfo> list = userInfoService.getUserInfo(ids);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化用户个人信息对象
	 * @return userLoginRegister
	 */
	public UserInfo setUserInfo(Long userId){
		try {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(userId);
			userInfo.setBirthday("1990-12-9");
			userInfo.setProvinceId(1l);
			userInfo.setCityId(1l);
			userInfo.setCountyId(2l);
			userInfo.setIsVisible(new Byte("1"));
			userInfo.setCtime(System.currentTimeMillis());
			userInfo.setUtime(System.currentTimeMillis());
			userInfo.setIp("192.168.110.119");
			return userInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
