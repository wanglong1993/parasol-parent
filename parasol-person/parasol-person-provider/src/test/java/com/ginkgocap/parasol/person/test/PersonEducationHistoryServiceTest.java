package com.ginkgocap.parasol.person.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.model.PersonEducationHistory;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.person.service.PersonEducationHistoryService;

public class PersonEducationHistoryServiceTest  extends TestBase implements Test  {

	@Resource
	private PersonEducationHistoryService personEducationHistoryService;
	@Resource
	private PersonBasicService personBasicService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 添加人脉自定义
	 */
	@org.junit.Test
	public void testCreatePersonEducationHistory(){
		try {
			Long personId=3953254564364289l;
			PersonBasic personBasic = personBasicService.getPersonBasic(personId);
			if(!ObjectUtils.isEmpty(personBasic)){
			List<PersonEducationHistory> list =setPersonEducationHistoryList(personId);
			List<PersonEducationHistory> ids =personEducationHistoryService.createPersonEducationHistoryByList(list,personId);
			for (PersonEducationHistory id1 : ids) {
			Assert.assertTrue(id1!=null && id1.getId()>0l);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改人脉自定义信息
	 */
	@org.junit.Test
	public void testUpdatePersonEducationHistory(){
		try {
			List<Long> ids = personEducationHistoryService.getIdList(3953254564364289l);
			List<PersonEducationHistory> list  =personEducationHistoryService.getIdList(ids);
			list=personEducationHistoryService.createPersonEducationHistoryByList(list, 3953254564364289l);
//			personEducationHistoryService.
			Assert.assertTrue(list!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 根据人脉Id获取自定义的id
	 */
	@org.junit.Test
	public void testGetIdList(){
		try {
			List<Long> ids = personEducationHistoryService.getIdList(3953254564364289l);
			Assert.assertTrue(ids!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id列表批量删除人脉自定义行业
	 */
	@org.junit.Test
	public void testRealDeletePersonEducationHistoryList(){
		try {
			boolean bl = personEducationHistoryService.realDeletePersonEducationHistoryList(personEducationHistoryService.getIdList(3953254564364289l));
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 初始化人脉自定义对象列表.
	 * @param userId
	 * @param count
	 * @return PersonEducationHistory
	 */
	public List<PersonEducationHistory> setPersonEducationHistoryList(Long personId){
		List<PersonEducationHistory> list = new ArrayList<PersonEducationHistory>();
		Long[] industryIds =new Long[]{1l,2l,3l,4l,5l};
		for (int i = 0; i < industryIds.length; i++) {
			list.add(setPersonEducationHistory(personId,industryIds[i]));
		}
		return list;
	}
	/**
	 * 初始化人脉自定义对象.
	 * @param personId
	 * @return PersonEducationHistory
	 */
	public PersonEducationHistory setPersonEducationHistory(Long personId,Long industryId){
		Long ctime=System.currentTimeMillis();
		PersonEducationHistory personEducationHistory = new  PersonEducationHistory();
		personEducationHistory.setPersonId(personId);
		personEducationHistory.setSchool("家里墩大学"+industryId);
		personEducationHistory.setMajor("软件工程"+industryId);
		personEducationHistory.setDegree(new Byte("1"));
		personEducationHistory.setBeginTime(ctime);
		personEducationHistory.setEndTime(ctime);
		personEducationHistory.setDescription("非常想念在学把妹的时光"+industryId);
		personEducationHistory.setIp("119.10.29.28");
		return personEducationHistory;
	}
	
}
