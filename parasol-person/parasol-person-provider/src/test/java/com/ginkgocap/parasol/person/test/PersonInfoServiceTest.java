package com.ginkgocap.parasol.person.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.person.model.PersonInfo;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.person.service.PersonInfoService;

public class PersonInfoServiceTest  extends TestBase implements Test  {

//	@Resource
//	private PersonInfoService personInfoService;
//	@Resource
//	private PersonBasicService personBasicService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
//	/**
//	 * 创建人脉个人信息
//	 */
//	@org.junit.Test
//	public void testCreatePersonInfo(){
//		try {
//			Long id2 =personInfoService.createPersonInfo(setPersonInfo(3953254564364289l));
//			Assert.assertTrue(id2!=null && id2>0l);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 修改人脉个人信息
//	 */
//	@org.junit.Test
//	public void testUpdatePersonInfo(){
//		try {
//			PersonInfo personInfo = personInfoService.getPersonInfo(3953254564364289l);
//			boolean bl =personInfoService.updatePersonInfo(personInfo);
//			Assert.assertTrue(bl);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 根据id获取人脉个人信息
//	 */
//	@org.junit.Test
//	public void testGetPersonInfo(){
//		try {
//			PersonInfo list = personInfoService.getPersonInfo(3953254564364289l);
//			Assert.assertTrue(list==null);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 根据userId列表获取人脉个人信息列表
//	 */
//	@org.junit.Test
//	public void testGetPersonInfoList(){
//		try {
//			List<Long> ids=new ArrayList<Long>();
//			ids.add(3953254564364289l);
//			List<PersonInfo> list = personInfoService.getPersonInfo(ids);
//			Assert.assertTrue(list.size()>0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 初始化人脉个人信息对象
//	 * @return userLoginRegister
//	 */
//	public PersonInfo setPersonInfo(Long personId){
//		try {
//			PersonInfo personInfo = new PersonInfo();
//			personInfo.setPersonId(personId);
//			personInfo.setBirthday("1990-01-09");
//			personInfo.setProvinceId(1l);
//			personInfo.setCityId(1l);
//			personInfo.setCountyId(2l);
//			personInfo.setIsVisible(new Byte("1"));
//			personInfo.setCtime(System.currentTimeMillis());
//			personInfo.setUtime(System.currentTimeMillis());
//			personInfo.setIp("192.168.110.119");
//			return personInfo;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
