package com.ginkgocap.parasol.person.test;

import junit.framework.Test;
import junit.framework.TestResult;

public class PersonContactWayServiceTest  extends TestBase implements Test  {
//
//	@Resource
//	private UserLoginRegisterService userLoginRegisterService;
//	@Resource
//	private PersonBasicService personBasicService;
//	@Resource
//	private PersonContactWayService personContactWayService;
//	
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
//	/**
//	 * 创建人脉联系方式信息
//	 */
//	@org.junit.Test
//	public void testCreatePersonContactWay(){
//		try {
//			PersonBasic personBasic = personBasicService.getPersonBasic(3953254564364289l);
//			if(!ObjectUtils.isEmpty(personBasic)){
//			Long id2 =personContactWayService.createPersonContactWay(setPersonContactWay(3953254564364289l));
//			Assert.assertTrue(id2!=null && id2>0l);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 修改人脉联系方式信息
//	 */
//	@org.junit.Test
//	public void testUpdatePersonContactWay(){
//		try {
//			PersonContactWay personContactWay = personContactWayService.getPersonContactWay(3953254564364289l);
//			boolean bl =personContactWayService.updatePersonContactWay(personContactWay);
//			Assert.assertTrue(bl);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 根据id获取人脉联系方式信息
//	 */
//	@org.junit.Test
//	public void testGetPersonContactWay(){
//		try {
//			PersonContactWay list = personContactWayService.getPersonContactWay(3953254564364289l);
//			Assert.assertTrue(list==null);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}	
//	/**
//	 * 根据userId列表获取人脉联系方式信息列表
//	 */
//	@org.junit.Test
//	public void testGetPersonContactWayList(){
//		try {
//			List<Long> ids=new ArrayList<Long>();
//			ids.add(1l);
//			List<PersonContactWay> list = personContactWayService.getPersonContactWayList(ids);
//			Assert.assertTrue(list.size()>0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 初始化人脉联系方式信息对象
//	 * @return userLoginRegister
//	 */
//	public PersonContactWay setPersonContactWay(Long personId){
//		try {
//			PersonContactWay personContactWay = new PersonContactWay();
//			personContactWay.setPersonId(personId);
//			personContactWay.setCellphone("13716687903");
//			personContactWay.setEmail("51022036@qq.com");
//			personContactWay.setWeixin("12e4323");
//			personContactWay.setQq("432432");
//			personContactWay.setWeibo("r32323");
//			personContactWay.setIsVisible(new Byte("1"));
//			personContactWay.setCtime(System.currentTimeMillis());
//			personContactWay.setUtime(System.currentTimeMillis());
//			personContactWay.setIp("192.168.110.119");
//			return personContactWay;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
