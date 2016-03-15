package com.ginkgocap.parasol.person.service;

import java.util.List;

import com.ginkgocap.parasol.person.exception.PersonDefinedServiceException;
import com.ginkgocap.parasol.person.model.PersonDefined;

/**
 * 操作人脉自定义接口定义
 * 
 */
public interface PersonDefinedService {

	/**
	 * 添加人脉自定义
	 * @param list 
	 * @param id
	 * @return List
	 * @throws PersonDefinedServiceException
	 */
	public List<PersonDefined> createPersonDefinedByList(List<PersonDefined> list,Long id) throws PersonDefinedServiceException; 
	/**
	 * 修改人脉自定义
	 * @param list 
	 * @param id
	 * @return List
	 * @throws PersonDefinedServiceException
	 */
	public List<PersonDefined> updatePersonDefinedByList(List<PersonDefined> list,Long id) throws PersonDefinedServiceException; 
	
	/**
	 * 根据id获取人脉自定义ID列表
	 * @param id 
	 * @return list
	 * @throws PersonDefinedServiceException
	 */
	public List<Long> getIdList(Long id) throws PersonDefinedServiceException;
	/**
	 * 根据Id列表获取人脉自定义列表
	 * @param ids 
	 * @return list
	 * @throws PersonDefinedServiceException
	 */
	public List<PersonDefined> getIdList(List<Long> ids) throws PersonDefinedServiceException;

	
	/**
	 * 根据id列表批量删除人脉自定义
	 * @param list 
	 * @return boolean
	 * @throws PersonDefinedServiceException
	 */
	public boolean realDeletePersonDefinedList(List<Long> list) throws PersonDefinedServiceException;
	
}
