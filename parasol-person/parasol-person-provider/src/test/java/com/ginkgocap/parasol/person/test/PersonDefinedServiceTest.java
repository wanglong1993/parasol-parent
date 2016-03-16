package com.ginkgocap.parasol.person.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.model.PersonContactWay;
import com.ginkgocap.parasol.person.model.PersonDefined;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.person.service.PersonDefinedService;

public class PersonDefinedServiceTest  extends TestBase implements Test  {

	@Resource
	private PersonDefinedService personDefinedService;
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
	public void testCreatePersonDefined(){
		try {
			Long personId=3953254564364289l;
			PersonBasic personBasic = personBasicService.getPersonBasic(personId);
			if(!ObjectUtils.isEmpty(personBasic)){
			List<PersonDefined> list =setPersonDefinedList(personId);
			List<PersonDefined> ids =personDefinedService.createPersonDefinedByList(list,personId);
			for (PersonDefined id1 : ids) {
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
	public void testUpdatePersonDefined(){
		try {
			List<Long> ids = personDefinedService.getIdList(3953254564364289l);
			List<PersonDefined> list  =personDefinedService.getIdList(ids);
			list=personDefinedService.updatePersonDefinedByList(list, 3953254564364289l);
//			personDefinedService.
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
			List<Long> ids = personDefinedService.getIdList(3953254564364289l);
			Assert.assertTrue(ids!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id列表批量删除人脉自定义行业
	 */
	@org.junit.Test
	public void testRealDeletePersonDefinedList(){
		try {
			boolean bl = personDefinedService.realDeletePersonDefinedList(personDefinedService.getIdList(3953254564364289l));
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 初始化人脉自定义对象列表.
	 * @param userId
	 * @param count
	 * @return PersonDefined
	 */
	public List<PersonDefined> setPersonDefinedList(Long personId){
		List<PersonDefined> list = new ArrayList<PersonDefined>();
		Long[] industryIds =new Long[]{1l,2l,3l,4l,5l};
		for (int i = 0; i < industryIds.length; i++) {
			list.add(setPersonDefined(personId,industryIds[i]));
		}
		return list;
	}
	/**
	 * 初始化人脉自定义对象.
	 * @param personId
	 * @return PersonDefined
	 */
	public PersonDefined setPersonDefined(Long personId,Long industryId){
		Long ctime=System.currentTimeMillis();
		PersonDefined personDefined = new  PersonDefined();
		personDefined.setPersonId(personId);
		personDefined.setPersonDefinedModel("自定义模块"+industryId);
		personDefined.setPersonDefinedFiled("自定义字段"+industryId);
		personDefined.setPersonDefinedValue("自定义值"+industryId);
		personDefined.setCtime(ctime);
		personDefined.setUtime(ctime);
		personDefined.setIp("119.10.29.28");
		return personDefined;
	}
	
}
