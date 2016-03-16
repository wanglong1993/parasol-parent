package com.ginkgocap.parasol.person.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.model.PersonWorkHistory;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.person.service.PersonWorkHistoryService;

public class PersonWorkHistoryServiceTest  extends TestBase implements Test  {

	@Resource
	private PersonWorkHistoryService personWorkHistoryService;
	@Resource
	private PersonBasicService personBasicService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 添加人脉工作经历
	 */
	@org.junit.Test
	public void testCreatePersonWorkHistory(){
		try {
			Long personId=3953254564364289l;
			PersonBasic personBasic = personBasicService.getPersonBasic(personId);
			if(!ObjectUtils.isEmpty(personBasic)){
			List<PersonWorkHistory> list =setPersonWorkHistoryList(personId);
			List<PersonWorkHistory> ids =personWorkHistoryService.createPersonWorkHistoryByList(list,personId);
			for (PersonWorkHistory id1 : ids) {
			Assert.assertTrue(id1!=null && id1.getId()>0l);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改人脉工作经历信息
	 */
	@org.junit.Test
	public void testUpdatePersonWorkHistory(){
		try {
			List<Long> ids = personWorkHistoryService.getIdList(3953254564364289l);
			List<PersonWorkHistory> list  =personWorkHistoryService.getIdList(ids);
			list=personWorkHistoryService.updatePersonWorkHistoryByList(list, 3953254564364289l);
//			personWorkHistoryService.
			Assert.assertTrue(list!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 根据人脉Id获取工作经历的id
	 */
	@org.junit.Test
	public void testGetIdList(){
		try {
			List<Long> ids = personWorkHistoryService.getIdList(3953254564364289l);
			Assert.assertTrue(ids!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id列表批量删除人脉工作经历行业
	 */
	@org.junit.Test
	public void testRealDeletePersonWorkHistoryList(){
		try {
			boolean bl = personWorkHistoryService.realDeletePersonWorkHistoryList(personWorkHistoryService.getIdList(3953254564364289l));
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 初始化人脉工作经历对象列表.
	 * @param userId
	 * @param count
	 * @return PersonWorkHistory
	 */
	public List<PersonWorkHistory> setPersonWorkHistoryList(Long personId){
		List<PersonWorkHistory> list = new ArrayList<PersonWorkHistory>();
		Long[] industryIds =new Long[]{1l,2l,3l,4l,5l};
		for (int i = 0; i < industryIds.length; i++) {
			list.add(setPersonWorkHistory(personId,industryIds[i]));
		}
		return list;
	}
	/**
	 * 初始化人脉工作经历对象.
	 * @param personId
	 * @return PersonWorkHistory
	 */
	public PersonWorkHistory setPersonWorkHistory(Long personId,Long industryId){
		Long ctime=System.currentTimeMillis();
		PersonWorkHistory personWorkHistory = new  PersonWorkHistory();
		personWorkHistory.setPersonId(personId);
		personWorkHistory.setIncName("公司名称"+industryId);
		personWorkHistory.setPosition("职位"+industryId);
		personWorkHistory.setBeginTime(ctime);
		personWorkHistory.setEndTime(ctime);
		personWorkHistory.setDescription("非常想念在公司天天加班的时光"+industryId);
		personWorkHistory.setIp("119.10.29.28");
		return personWorkHistory;
	}
	
}
