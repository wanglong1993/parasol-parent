package com.ginkgocap.parasol.user.test;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.model.UserOrgPerCusRel;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserOrgPerCusRelService;

public class UserOrgPerCusRelServiceTest  extends TestBase  implements Test{

	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private UserOrgPerCusRelService userOrgPerCusRelService;
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 添加用户好友人脉组织客户关系
	 */
	@org.junit.Test
	public void testCreateUserBasic(){
		try {
			Long id=userLoginRegisterService.getId("13677687623");
			Long id2 =userOrgPerCusRelService.createUserOrgPerCusRel(setCreateUserOrgPerCusRel(id,new Byte("2")));
			Assert.assertTrue(id2!=null && id2>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改用户好友备注名
	 */
	@org.junit.Test
	public void testUpdateName(){
		try {
			Long userId=userLoginRegisterService.getId("13677687623");
			Long friendId=userLoginRegisterService.getId("13677687629");
			boolean bl =userOrgPerCusRelService.updateName(userId,friendId, "张三锋");
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据userId获取用户我的里面的个人好友列表
	 */
	@org.junit.Test
	public void testGetUserFriendlyList(){
		try {
			Long userId=userLoginRegisterService.getId("13677687623");
			List<UserOrgPerCusRel> list =userOrgPerCusRelService.getUserFriendlyList(0,1,userId);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 根据userId获取用户我的里面的组织好友列表
	 */
	@org.junit.Test
	public void testGetOrgFriendlylList(){
		try {
			Long userId=userLoginRegisterService.getId("13677687623");
			List<UserOrgPerCusRel> list =userOrgPerCusRelService.getOrgFriendlylList(1,1,userId);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据userId获取用户通讯录个人和组织好友列表
	 */
	@org.junit.Test
	public void testGetUserAndOrgFriendlyList(){
		List<UserOrgPerCusRel> list =null;
		try {
			Long userId= userLoginRegisterService.getId("13677687623");
			list =userOrgPerCusRelService.getUserAndOrgFriendlyList(0,2,userId);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(list!=null && list.size()>0);
//			e.getMessage();
//			e.getErrorCode();
		}
	}
	/**
	 * 删除用户好友
	 */
	@org.junit.Test
	public void testDeleteFriendly(){
		try {
			Long friendId=userLoginRegisterService.getId("13677687625");
			Boolean bl =userOrgPerCusRelService.deleteFriendly(friendId);
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 初始化用户基本信息对象
	 * @return userLoginRegister
	 */
	public UserOrgPerCusRel setCreateUserOrgPerCusRel(Long userId,Byte releationType){
		try {
			Long ctime=System.currentTimeMillis();
			UserOrgPerCusRel userOrgPerCusRel = new UserOrgPerCusRel();
			userOrgPerCusRel.setUserId(userId);
			userOrgPerCusRel.setFriendId(userLoginRegisterService.getId("13677687625"));
			userOrgPerCusRel.setReleationType(releationType);
			userOrgPerCusRel.setCtime(ctime);
			userOrgPerCusRel.setUtime(ctime);
			return userOrgPerCusRel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
