package com.ginkgocap.parasol.user.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.model.UserOrganExt;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserOrganExtService;

public class UserOrganExtServiceTest  extends TestBase implements Test  {

	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private UserOrganExtService userOrganExtService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 创建组织用户基本信息
	 */
	@org.junit.Test
	public void testCreateUserOrganExt(){
		try {
			Long id=userLoginRegisterService.getId("15966467469");
			Long id2 =userOrganExtService.createUserOrganExt(setUserOrganExt(id));
			Assert.assertTrue(id2!=null && id2>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改组织用户基本信息
	 */
	@org.junit.Test
	public void testUpdateUserOrganExt(){
		try {
			Long id=userLoginRegisterService.getId("15966467469");
			boolean bl =userOrganExtService.updateUserOrganExt(setUserOrganExt(id));
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取单个组织用户基本信息
	 */
	@org.junit.Test
	public void testGetOrganBasic(){
		try {
			Long id=userLoginRegisterService.getId("15966467469");
			UserOrganExt userOrganExt =userOrganExtService.getUserOrganExt(id);
			Assert.assertTrue(userOrganExt!=null);
//			FileIndex fileIndex=(FileIndex)userOrganBasic.getFileIndexMap().get(userOrganBasic.getBusinessLicencePicId());
//			fileIndex.getFilePath();
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
			List<UserOrganExt> list = userOrganExtService.getUserOrganExtList(userIds);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化用户基本信息对象
	 * @return userLoginRegister
	 */
	public UserOrganExt setUserOrganExt(Long userId){
		try {
			Long ctime=System.currentTimeMillis();
			UserOrganExt userOrganExt = new UserOrganExt();
			userOrganExt.setUserId(userId);
			userOrganExt.setName("2222百品网络科技有限公司");
			userOrganExt.setShortName("百品");
			userOrganExt.setRegFrom("gintongapp");
			userOrganExt.setBrief("2222百品网络科技有限公司");
			userOrganExt.setPhone("010-89765623");
			userOrganExt.setOrgType("2222一般企业");
//			userOrganExt.setAuth(new Byte("-1"));
//			userOrganExt.setPicPath("/webserver/data/pic/140/11.jgp");
//			userOrganExt.setStatus(new Byte("1"));
			userOrganExt.setCtime(ctime);
			userOrganExt.setUtime(ctime);
			userOrganExt.setIp("192.168.110.119");
			userOrganExt.setBusinessLicencePicId(3918187813142533l);
			userOrganExt.setCompanyContacts("张三");
			userOrganExt.setCompanyContactsMobile("13716683980");
			userOrganExt.setIdcardFrontPicId(3918504642478090l);
			userOrganExt.setIdcardBackPicId(3918553426427924l);
			return userOrganExt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
