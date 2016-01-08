package com.ginkgocap.parasol.user.test;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.user.model.UserExt;
import com.ginkgocap.parasol.user.service.UserExtService;
import com.ginkgocap.parasol.user.service.UserInterestIndustryService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

public class UserExtServiceTest  extends TestBase implements Test  {

	@Resource
	private UserInterestIndustryService userInterestIndustryService;
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private UserExtService userExtService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 创建用户基本信息
	 */
	@org.junit.Test
	public void testCreateUserExt(){
		try {
			Long id=userLoginRegisterService.getId("13677687632");
			Long id2 =userExtService.createUserExt(setUserExt(id));
			Assert.assertTrue(id2!=null && id2>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改用户基本信息
	 */
	@org.junit.Test
	public void testUpdateUserExt(){
		try {
			Long id=userLoginRegisterService.getId("15966467469");
			boolean bl =userExtService.updateUserExt(setUserExt(id));
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
	 * 根据第三级行业ID获取用户列表
	 */
	@org.junit.Test
	public void testGetUserListByThirdIndustryId(){
		try {
			List<UserExt> list =userExtService.getUserListByThirdIndustryId(0, 10, 1308l);
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
	 * 根据用户Id获取感兴趣的id
	 */
	@org.junit.Test
	public void testGetUserExt(){
		try {
//			Long id=userLoginRegisterService.getId("13677687623");
			UserExt userExt = userExtService.getUserExt(3920063652692018l);
			Assert.assertTrue(userExt!=null);
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
	public void testGetUserExtList(){
		try {
			List<Long> userIds=userInterestIndustryService.getUserIdListByIndustryId(0, 1, 3l);
			List<UserExt> list = userExtService.getUserExtList(userIds);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据用户所在县地区id获取用户id列表
	 */
	@org.junit.Test
	public void testGetUserExtListByCountryId(){
		try {
			List<UserExt> list = userExtService.getUserExtListByCountryId(1, 1, 111l);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 根据用户所在地区省id获取用户id列表
	 */
	@org.junit.Test
	public void testGetUserExtListByProvinceId(){
		try {
			List<UserExt> list = userExtService.getUserExtListByProvinceId(1, 1, 1l);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 初始化用户基本信息对象
	 * @return userLoginRegister
	 */
	public UserExt setUserExt(Long userId){
		try {
			Long ctime=System.currentTimeMillis();
			UserExt userExt = new UserExt();
			userExt.setUserId(userId);
			userExt.setName("安详");
			userExt.setThirdIndustryId(1308l);
			Byte sex=1;
//			UserExt.setProvinceId(null);
//			UserExt.setCityId(null);
//			UserExt.setCountyId(null);
			userExt.setUtime(ctime);
//			UserExt.setCompanyName("金桐网");
//			UserExt.setCompanyJob("科学家");
//			UserExt.setShortName("张家口");
//			UserExt.setDescription("");
			userExt.setCtime(System.currentTimeMillis());
			userExt.setUtime(System.currentTimeMillis());
			userExt.setIp("192.66.55.112");
			return userExt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
