package com.ginkgocap.parasol.user.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserWorkHistory;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserWorkHistoryService;

public class UserWorkHistoryServiceTest  extends TestBase implements Test  {

	@Resource
	private UserWorkHistoryService userWorkHistoryService;
	@Resource
	private UserBasicService userBasicService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 添加用户工作经历
	 */
	@org.junit.Test
	public void testCreateUserWorkHistory(){
		try {
			Long userId=3953254564364289l;
			UserBasic userBasic = userBasicService.getUserBasic(userId);
			if(!ObjectUtils.isEmpty(userBasic)){
			List<UserWorkHistory> list =setUserWorkHistoryList(userId);
			List<UserWorkHistory> ids =userWorkHistoryService.createUserWorkHistoryByList(list,userId);
			for (UserWorkHistory id1 : ids) {
			Assert.assertTrue(id1!=null && id1.getId()>0l);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改用户工作经历信息
	 */
	@org.junit.Test
	public void testUpdateUserWorkHistory(){
		try {
			List<Long> ids = userWorkHistoryService.getIdList(3953254564364289l);
			List<UserWorkHistory> list  =userWorkHistoryService.getIdList(ids);
			list=userWorkHistoryService.updateUserWorkHistoryByList(list, 3953254564364289l);
//			userWorkHistoryService.
			Assert.assertTrue(list!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 根据用户Id获取工作经历的id
	 */
	@org.junit.Test
	public void testGetIdList(){
		try {
			List<Long> ids = userWorkHistoryService.getIdList(3953254564364289l);
			Assert.assertTrue(ids!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id列表批量删除用户工作经历行业
	 */
	@org.junit.Test
	public void testRealDeleteUserWorkHistoryList(){
		try {
			boolean bl = userWorkHistoryService.realDeleteUserWorkHistoryList(userWorkHistoryService.getIdList(3953254564364289l));
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 初始化用户工作经历对象列表.
	 * @param userId
	 * @param count
	 * @return UserWorkHistory
	 */
	public List<UserWorkHistory> setUserWorkHistoryList(Long userId){
		List<UserWorkHistory> list = new ArrayList<UserWorkHistory>();
		Long[] industryIds =new Long[]{1l,2l,3l,4l,5l};
		for (int i = 0; i < industryIds.length; i++) {
			list.add(setUserWorkHistory(userId,industryIds[i]));
		}
		return list;
	}
	/**
	 * 初始化用户工作经历对象.
	 * @param userId
	 * @return UserWorkHistory
	 */
	public UserWorkHistory setUserWorkHistory(Long userId,Long industryId){
		Long ctime=System.currentTimeMillis();
		UserWorkHistory userWorkHistory = new  UserWorkHistory();
		userWorkHistory.setUserId(userId);
		userWorkHistory.setIncName("公司名称"+industryId);
		userWorkHistory.setPosition("职位"+industryId);
		userWorkHistory.setBeginTime("201603");
		userWorkHistory.setEndTime("201603");
		userWorkHistory.setDescription("非常想念在公司天天加班的时光"+industryId);
		userWorkHistory.setIp("119.10.29.28");
		return userWorkHistory;
	}
	
}
