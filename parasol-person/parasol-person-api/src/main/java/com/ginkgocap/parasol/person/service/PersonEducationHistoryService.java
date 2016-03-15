package com.ginkgocap.parasol.person.service;

import java.util.List;

import com.ginkgocap.parasol.person.exception.PersonEducationHistoryServiceException;
import com.ginkgocap.parasol.person.model.PersonEducationHistory;

/**
 * 操作人脉教育经历接口定义
 * 
 */
public interface PersonEducationHistoryService {

	/**
	 * 添加人脉教育经历
	 * @param list 
	 * @param id
	 * @return List
	 * @throws PersonEducationHistoryServiceException
	 */
	public List<PersonEducationHistory> createPersonEducationHistoryByList(List<PersonEducationHistory> list,Long id) throws PersonEducationHistoryServiceException; 
	
	/**
	 * 根据id获取人脉教育经历ID列表
	 * @param id 
	 * @return list
	 * @throws PersonEducationHistoryServiceException
	 */
	public List<Long> getIdList(Long id) throws PersonEducationHistoryServiceException;
	/**
	 * 根据Id列表获取人脉教育经历列表
	 * @param ids 
	 * @return list
	 * @throws PersonEducationHistoryServiceException
	 */
	public List<PersonEducationHistory> getIdList(List<Long> ids) throws PersonEducationHistoryServiceException;

	
	/**
	 * 根据id列表批量删除人脉教育经历
	 * @param list 
	 * @return boolean
	 * @throws PersonEducationHistoryServiceException
	 */
	public boolean realDeletePersonEducationHistoryList(List<Long> list) throws PersonEducationHistoryServiceException;
	
}
