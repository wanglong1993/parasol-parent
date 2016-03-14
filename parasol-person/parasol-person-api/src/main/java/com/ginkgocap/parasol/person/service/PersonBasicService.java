package com.ginkgocap.parasol.person.service;

import java.util.List;

import com.ginkgocap.parasol.person.exception.PersonBasicServiceException;
import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;

/**
 * 操作人脉基本资料接口定义
 * 
 */
public interface PersonBasicService {

	/**
	 * 创建人脉基本信息
	 * @param personBasic 
	 * @return Long
	 * @throws personBasicServiceException
	 */
	public Long createPersonBasic(PersonBasic personBasic) throws PersonBasicServiceException,UserLoginRegisterServiceException; 
	

	/**
	 * 修改人脉基本信息
	 * @param personBasic
	 * @return boolean
	 * @throws personBasicServiceException 
	 */
	public boolean updatePersonBasic(PersonBasic personBasic) throws PersonBasicServiceException,UserLoginRegisterServiceException; 

	/**
	 * 根据id获取人脉基本信息
	 * @param id
	 * @return personBasic
	 * @throws personBasicServiceException 
	 */
	public PersonBasic getPersonBasic(Long id) throws PersonBasicServiceException; 
	
	/**
	 * 根据id列表获取人脉基本信息列表
	 * @param ids 
	 * @return List
	 * @throws personBasicServiceException
	 */
	public List<PersonBasic> getPersonBasicList(List<Long> ids) throws PersonBasicServiceException;	
	
	/**
	 * 根据id真删除人脉基本信息
	 * @param id
	 * @return Boolean
	 * @throws personBasicServiceException
	 */
	public Boolean realDeletePersonBasic(Long id) throws PersonBasicServiceException;	

}
