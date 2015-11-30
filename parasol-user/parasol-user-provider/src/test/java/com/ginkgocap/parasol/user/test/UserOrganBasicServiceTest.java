package com.ginkgocap.parasol.user.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.model.UserOrganBasic;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserOrganBasicService;

public class UserOrganBasicServiceTest  extends TestBase implements Test  {

	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private UserOrganBasicService userOrganBasicService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 创建组织用户基本信息
	 */
	@org.junit.Test
	public void testCreateUserOrganBasic(){
		try {
			Long id=userLoginRegisterService.getId("51022036@qq.com");
			Long id2 =userOrganBasicService.createUserOrganBasic(setUserOrganBasic(id));
			Assert.assertTrue(id2!=null && id2>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改组织用户基本信息
	 */
	@org.junit.Test
	public void testUpdateUserOrganBasic(){
		try {
			Long id=userLoginRegisterService.getId("51022036@qq.com");
			boolean bl =userOrganBasicService.updateUserOrganBasic(setUserOrganBasic(id));
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据userId列表获取用户基本信息列表
	 */
	@org.junit.Test
	public void testGetUserBasecList(){
		try {
			Long id=userLoginRegisterService.getId("51022036@qq.com");
			List<Long> userIds=new ArrayList<Long>();
			userIds.add(id);
			List<UserOrganBasic> list = userOrganBasicService.getUserOrganBasecList(userIds);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 审核组织
	 */
	@org.junit.Test
	public void testUpdateAuth(){
		try {
			Long id=userLoginRegisterService.getId("51022036@qq.com");
			boolean bl = userOrganBasicService.updateAuth(id, new Byte("1"));
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改组织状态
	 */
	@org.junit.Test
	public void testUpdateStatus(){
		try {
			Long id=userLoginRegisterService.getId("51022036@qq.com");
			boolean bl = userOrganBasicService.updateStatus(id, new Byte("2"));
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 初始化用户基本信息对象
	 * @return userLoginRegister
	 */
	public UserOrganBasic setUserOrganBasic(Long userId){
		try {
			Long ctime=System.currentTimeMillis();
			UserOrganBasic userOrganBasic = new UserOrganBasic();
			userOrganBasic.setUserId(userId);
			userOrganBasic.setName("百品网络科技有限公司");
			userOrganBasic.setShortName("百品");
			userOrganBasic.setRegFrom("gintongapp");
			userOrganBasic.setBrief("百品网络科技有限公司");
			userOrganBasic.setPhone("010-89765623");
			userOrganBasic.setOrgType("一般企业");
			userOrganBasic.setAuth(new Byte("-1"));
			userOrganBasic.setPicPath("/webserver/data/pic/140/11.jgp");
			userOrganBasic.setStatus(new Byte("1"));
			userOrganBasic.setCtime(ctime);
			userOrganBasic.setUtime(ctime);
			userOrganBasic.setIp("192.168.110.119");
			return userOrganBasic;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
