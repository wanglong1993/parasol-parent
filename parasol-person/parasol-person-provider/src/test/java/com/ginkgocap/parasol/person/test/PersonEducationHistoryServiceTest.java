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
	 * 添加人脉教育经历
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
	 * 修改人脉教育经历信息
	 */
	@org.junit.Test
	public void testUpdatePersonEducationHistory(){
		try {
			List<Long> ids = personEducationHistoryService.getIdList(3953254564364289l);
			List<PersonEducationHistory> list  =personEducationHistoryService.getIdList(ids);
			list=personEducationHistoryService.updatePersonEducationHistoryByList(list, 3953254564364289l);
//			personEducationHistoryService.
			Assert.assertTrue(list!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 根据人脉Id获取教育经历的id
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
	 * 根据id列表批量删除人脉教育经历行业
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
	 * 初始化人脉教育经历对象列表.
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
	 * 初始化人脉教育经历对象.
	 * @param personId
	 * @return PersonEducationHistory
	 */
	public PersonEducationHistory setPersonEducationHistory(Long personId,Long industryId){
		Long ctime=System.currentTimeMillis();
		PersonEducationHistory personEducationHistory = new  PersonEducationHistory();
		personEducationHistory.setPersonId(personId);
		personEducationHistory.setSchool("家里墩大学"+industryId);
		personEducationHistory.setMajor("软件工程"+industryId);
		personEducationHistory.setDegree("中学");
		personEducationHistory.setBeginTime("201603");
		personEducationHistory.setEndTime("201603");
		personEducationHistory.setDescription("非常想念在学把妹的时光"+industryId);
		personEducationHistory.setIp("119.10.29.28");
		return personEducationHistory;
	}
	
}
