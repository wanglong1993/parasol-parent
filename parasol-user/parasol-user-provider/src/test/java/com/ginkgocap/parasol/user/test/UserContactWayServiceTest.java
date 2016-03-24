package com.ginkgocap.parasol.user.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserContactWay;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserContactWayService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

public class UserContactWayServiceTest  extends TestBase implements Test  {

	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private UserBasicService userBasicService;
	@Resource
	private UserContactWayService userContactWayService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 创建用户联系方式信息
	 */
	@org.junit.Test
	public void testCreateUserContactWay(){
		try {
			UserBasic userBasic = userBasicService.getUserBasic(3953254564364289l);
			if(!ObjectUtils.isEmpty(userBasic)){
			Long id2 =userContactWayService.createUserContactWay(setUserContactWay(3953254564364289l));
			Assert.assertTrue(id2!=null && id2>0l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改用户联系方式信息
	 */
	@org.junit.Test
	public void testUpdateUserContactWay(){
		try {
			UserContactWay userContactWay = userContactWayService.getUserContactWay(3953254564364289l);
			boolean bl =userContactWayService.updateUserContactWay(userContactWay);
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id获取用户联系方式信息
	 */
	@org.junit.Test
	public void testGetUserContactWay(){
		try {
			UserContactWay list = userContactWayService.getUserContactWay(3953254564364289l);
			Assert.assertTrue(list==null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 根据userId列表获取用户联系方式信息列表
	 */
	@org.junit.Test
	public void testGetUserContactWayList(){
		try {
			List<Long> ids=new ArrayList<Long>();
			ids.add(1l);
			List<UserContactWay> list = userContactWayService.getUserContactWayList(ids);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化用户联系方式信息对象
	 * @return userLoginRegister
	 */
	public UserContactWay setUserContactWay(Long userId){
		try {
			UserContactWay userContactWay = new UserContactWay();
			userContactWay.setUserId(userId);
			userContactWay.setCellphone("13716687903");
			userContactWay.setEmail("51022036@qq.com");
			userContactWay.setWeixin("12e4323");
			userContactWay.setQq("432432");
			userContactWay.setWeibo("r32323");
			userContactWay.setIsVisible(new Byte("1"));
			userContactWay.setCtime(System.currentTimeMillis());
			userContactWay.setUtime(System.currentTimeMillis());
			userContactWay.setIp("192.168.110.119");
			return userContactWay;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
