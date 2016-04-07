package com.ginkgocap.parasol.user.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.model.UserConfig;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserConfigerService;

public class UserConfigServiceTest  extends TestBase implements Test  {

	@Resource
	private UserConfigerService userConfigerService;
	@Resource
	private UserBasicService userBasicService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 创建用户设置
	 */
	@org.junit.Test
	public void testCreateUserConfig(){
		try {
			Long id2 =userConfigerService.createUserConfig(setUserConfig(3953254564364289l));
			Assert.assertTrue(id2!=null && id2>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改用户设置
	 */
	@org.junit.Test
	public void testUpdateUserConfig(){
		try {
			UserConfig userConfig = userConfigerService.getUserConfig(3953254564364289l);
			boolean bl =userConfigerService.updateUserConfig(userConfig);
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id获取用户设置
	 */
	@org.junit.Test
	public void testGetUserConfig(){
		try {
			UserConfig list = userConfigerService.getUserConfig(3953254564364289l);
			Assert.assertTrue(list==null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据userId列表获取用户设置列表
	 */
	@org.junit.Test
	public void testGetUserConfigList(){
		try {
			List<Long> ids=new ArrayList<Long>();
			ids.add(3953254564364289l);
			List<UserConfig> list = userConfigerService.getUserConfig(ids);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化用户设置对象
	 * @return userLoginRegister
	 */
	public UserConfig setUserConfig(Long userId){
		try {
			UserConfig userConfig = new UserConfig();
			userConfig.setUserId(userId);
			userConfig.setHomePageVisible(new Byte("2"));
			userConfig.setEvaluateVisible(new Byte("2"));
			userConfig.setAutosave(new Byte("0"));
			userConfig.setCtime(System.currentTimeMillis());
			userConfig.setUtime(System.currentTimeMillis());
			return userConfig;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
