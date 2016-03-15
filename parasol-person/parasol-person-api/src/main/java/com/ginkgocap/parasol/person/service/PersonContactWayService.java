package com.ginkgocap.parasol.person.service;

import java.util.List;

import com.ginkgocap.parasol.person.exception.PersonContactWayServiceException;
import com.ginkgocap.parasol.person.model.PersonContactWay;

/**
 * 操作人脉联系方式接口定义
 * 
 */
public interface PersonContactWayService {

	/**
	 * 创建人脉联系方式
	 * @param personContactWay 
	 * @return Long
	 * @throws PersonContactWayServiceException
	 */
	public Long createPersonContactWay(PersonContactWay personContactWay) throws PersonContactWayServiceException; 
	

	/**
	 * 修改人脉联系方式
	 * @param personContactWay
	 * @return boolean
	 * @throws PersonContactWayServiceException 
	 */
	public boolean updatePersonContactWay(PersonContactWay personContactWay) throws PersonContactWayServiceException; 

	/**
	 * 根据id获取人脉联系方式
	 * @param id
	 * @return personContactWay
	 * @throws PersonContactWayServiceException 
	 */
	public PersonContactWay getPersonContactWay(Long id) throws PersonContactWayServiceException; 
	
	/**
	 * 根据id列表获取人脉联系方式列表
	 * @param ids 
	 * @return List
	 * @throws PersonContactWayServiceException
	 */
	public List<PersonContactWay> getPersonContactWayList(List<Long> ids) throws PersonContactWayServiceException;	
	
	/**
	 * 根据id真删除人脉联系方式
	 * @param id
	 * @return Boolean
	 * @throws PersonContactWayServiceException
	 */
	public Boolean realDeletePersonContactWay(Long id) throws PersonContactWayServiceException;	

}
