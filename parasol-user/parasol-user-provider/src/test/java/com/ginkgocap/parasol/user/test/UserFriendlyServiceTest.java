package com.ginkgocap.parasol.user.test;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.model.UserFriendly;
import com.ginkgocap.parasol.user.service.UserFriendlyService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

public class UserFriendlyServiceTest  extends TestBase  implements Test{

	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private UserFriendlyService userFriendlyService;
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 添加用户好友人脉组织客户关系
	 */
	@org.junit.Test
	public void testCreateUserFriendly(){
		try {
			Long id=userLoginRegisterService.getId("13777768734");
			Long id2 =userFriendlyService.createUserFriendly(setUserFriendly(id,new Byte("0")),false);
			Assert.assertTrue(id2!=null && id2>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改用户好友备注名
	 */
	@org.junit.Test
	public void testUpdateStatus(){
		try {
			Long userId=userLoginRegisterService.getId("13677687623");
			Long friendId=userLoginRegisterService.getId("13677687625");
			boolean bl =userFriendlyService.updateStatus(userId, friendId, new Byte("1"));
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据userId获取用户我的里面的个人好友列表
	 */
	@org.junit.Test
	public void testDeleteFriendly(){
		try {
			Long userId=userLoginRegisterService.getId("13677687623");
			Long friendId=userLoginRegisterService.getId("13677687625");
			boolean bl=userFriendlyService.deleteFriendly(userId, friendId);
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	/**
	 * 初始化用户好友关系
	 * @return userLoginRegister
	 */
	public UserFriendly setUserFriendly(Long userId,Byte status){
		try {
			Long ctime=System.currentTimeMillis();
			UserFriendly userFriendly = new UserFriendly();
			userFriendly.setUserId(userId);
			userFriendly.setFriendId(userLoginRegisterService.getId("13777768733"));
			userFriendly.setStatus(status);
			userFriendly.setContent("我是芈月，请加我");
			userFriendly.setCtime(ctime);
			userFriendly.setUtime(ctime);
			userFriendly.setAppId("3432432");
			return userFriendly;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
