package com.ginkgocap.parasol.user.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserEducationHistory;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserEducationHistoryService;

public class UserEducationHistoryServiceTest  extends TestBase implements Test  {

	@Resource
	private UserEducationHistoryService userEducationHistoryService;
	@Resource
	private UserBasicService userBasicService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 添加用户教育经历
	 */
	@org.junit.Test
	public void testCreateUserEducationHistory(){
		try {
			Long userId=3953254564364289l;
			UserBasic userBasic = userBasicService.getUserBasic(userId);
			if(!ObjectUtils.isEmpty(userBasic)){
			List<UserEducationHistory> list =setUserEducationHistoryList(userId);
			List<UserEducationHistory> ids =userEducationHistoryService.createUserEducationHistoryByList(list,userId);
			for (UserEducationHistory id1 : ids) {
			Assert.assertTrue(id1!=null && id1.getId()>0l);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改用户教育经历信息
	 */
	@org.junit.Test
	public void testUpdateUserEducationHistory(){
		try {
			List<Long> ids = userEducationHistoryService.getIdList(3953254564364289l);
			List<UserEducationHistory> list  =userEducationHistoryService.getIdList(ids);
			list=userEducationHistoryService.updateUserEducationHistoryByList(list, 3953254564364289l);
//			userEducationHistoryService.
			Assert.assertTrue(list!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 根据用户Id获取教育经历的id
	 */
	@org.junit.Test
	public void testGetIdList(){
		try {
			List<Long> ids = userEducationHistoryService.getIdList(3953254564364289l);
			Assert.assertTrue(ids!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id列表批量删除用户教育经历行业
	 */
	@org.junit.Test
	public void testRealDeleteUserEducationHistoryList(){
		try {
			boolean bl = userEducationHistoryService.realDeleteUserEducationHistoryList(userEducationHistoryService.getIdList(3953254564364289l));
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 初始化用户教育经历对象列表.
	 * @param userId
	 * @param count
	 * @return UserEducationHistory
	 */
	public List<UserEducationHistory> setUserEducationHistoryList(Long userId){
		List<UserEducationHistory> list = new ArrayList<UserEducationHistory>();
		Long[] industryIds =new Long[]{1l,2l,3l,4l,5l};
		for (int i = 0; i < industryIds.length; i++) {
			list.add(setUserEducationHistory(userId,industryIds[i]));
		}
		return list;
	}
	/**
	 * 初始化用户教育经历对象.
	 * @param userId
	 * @return UserEducationHistory
	 */
	public UserEducationHistory setUserEducationHistory(Long userId,Long industryId){
		Long ctime=System.currentTimeMillis();
		UserEducationHistory userEducationHistory = new  UserEducationHistory();
		userEducationHistory.setUserId(userId);
		userEducationHistory.setSchool("家里墩大学"+industryId);
		userEducationHistory.setMajor("软件工程"+industryId);
		userEducationHistory.setDegree("中学");
		userEducationHistory.setBeginTime("201603");
		userEducationHistory.setEndTime("201603");
		userEducationHistory.setDescription("非常想念在学把妹的时光"+industryId);
		userEducationHistory.setIp("119.10.29.28");
		return userEducationHistory;
	}
	
}
