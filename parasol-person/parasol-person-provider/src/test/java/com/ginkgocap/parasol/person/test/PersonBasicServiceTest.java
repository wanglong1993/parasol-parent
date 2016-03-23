package com.ginkgocap.parasol.person.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;

import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

public class PersonBasicServiceTest  extends TestBase implements Test  {

	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private PersonBasicService personBasicService;
	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	/**
	 * 创建人脉基本信息
	 */
	@org.junit.Test
	public void testCreatePersonBasic(){
		try {
			Long id=userLoginRegisterService.getId("13677687632");
			Long id2 =personBasicService.createPersonBasic(setPersonBasic(id));
			Assert.assertTrue(id2!=null && id2>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改人脉基本信息
	 */
	@org.junit.Test
	public void testUpdatePersonBasic(){
		try {
			PersonBasic personBasic = personBasicService.getPersonBasic(3953254564364289l);
			boolean bl =personBasicService.updatePersonBasic(personBasic);
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id获取人脉基本信息
	 */
	@org.junit.Test
	public void testGetPersonBasic(){
		try {
			PersonBasic list = personBasicService.getPersonBasic(3953254564364289l);
			Assert.assertTrue(list==null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据userId列表获取人脉基本信息列表
	 */
	@org.junit.Test
	public void testGetPersonBasicList(){
		try {
			List<Long> ids=new ArrayList<Long>();
			ids.add(1l);
			List<PersonBasic> list = personBasicService.getPersonBasicList(ids);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化人脉基本信息对象
	 * @return userLoginRegister
	 */
	public PersonBasic setPersonBasic(Long userId){
		try {
			PersonBasic personBasic = new PersonBasic();
			personBasic.setUserId(userId);
			personBasic.setPersonId(11l);
			personBasic.setPersonType(new Byte("1"));
			personBasic.setName("双又");
			personBasic.setGender(new Byte("1"));
			personBasic.setCompany("金桐网");
			personBasic.setPosition("工程师");
			personBasic.setPicId(1l);
			personBasic.setCountryId(1l);
			personBasic.setCityId(2l);
			personBasic.setCountyId(3l);
			personBasic.setAddress("北京市大兴区");
			personBasic.setRemark("我的个人介绍");
			personBasic.setFirstIndustryId(1l);
			personBasic.setSecondIndustryId(2l);
			personBasic.setCtime(System.currentTimeMillis());
			personBasic.setUtime(System.currentTimeMillis());
			personBasic.setIp("192.168.110.119");
			return personBasic;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
