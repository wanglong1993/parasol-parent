package com.ginkgocap.parasol.user.test;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.model.UserOrganBasic;
import com.ginkgocap.parasol.user.service.UserOrganBasicService;
import com.ginkgocap.parasol.user.service.UserInterestIndustryService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

public class UserOrganBasicServiceTest  extends TestBase implements Test  {

	@Resource
	private UserInterestIndustryService userInterestIndustryService;
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
	 * 创建用户基本信息
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
	 * 修改用户基本信息
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
	 * 根据userId列表获取用户基本信息列表
	 */
	@org.junit.Test
	public void testGetUserBasecList(){
		try {
			List<Long> userIds=userInterestIndustryService.getUserIdListByIndustryId(0, 1, 3l);
			List<UserOrganBasic> list = userOrganBasicService.getUserBasecList(userIds);
			Assert.assertTrue(list.size()>0);
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
			userOrganBasic.setNameFirst("bpkjyxgs");
			userOrganBasic.setNameIndex("bpkjyxgs");
			userOrganBasic.setNameIndexAll("bpkjyxgs");
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
