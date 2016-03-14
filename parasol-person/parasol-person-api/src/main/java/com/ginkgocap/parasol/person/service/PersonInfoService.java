package com.ginkgocap.parasol.person.service;

import java.util.List;

import com.ginkgocap.parasol.person.exception.PersonInfoServiceException;
import com.ginkgocap.parasol.person.model.PersonInfo;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;

/**
 * 个人情况
 * 
 */
public interface PersonInfoService {

	/**
	 * 创建人脉个人情况
	 * @param personInfo 
	 * @return Long
	 * @throws PersonInfoServiceException
	 */
	public Long createPersonInfo(PersonInfo personInfo) throws PersonInfoServiceException,UserLoginRegisterServiceException; 
	

	/**
	 * 修改人脉个人情况
	 * @param personInfo
	 * @return boolean
	 * @throws PersonInfoServiceException 
	 */
	public boolean updatePersonInfo(PersonInfo personInfo) throws PersonInfoServiceException,UserLoginRegisterServiceException; 

	/**
	 * 根据id获取人脉个人情况
	 * @param id
	 * @return personInfo
	 * @throws PersonInfoServiceException 
	 */
	public PersonInfo getPersonInfo(Long id) throws PersonInfoServiceException; 
	
	/**
	 * 根据id列表获取人脉个人情况列表
	 * @param ids 
	 * @return List
	 * @throws PersonInfoServiceException
	 */
	public List<PersonInfo> getPersonInfo(List<Long> ids) throws PersonInfoServiceException;	
	
	/**
	 * 根据id真删除人脉个人情况
	 * @param id
	 * @return Boolean
	 * @throws PersonInfoServiceException
	 */
	public Boolean realDeletePersonInfo(Long id) throws PersonInfoServiceException;	

}
