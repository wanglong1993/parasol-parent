package com.ginkgocap.parasol.person.service;

import java.util.List;

import com.ginkgocap.parasol.person.exception.PersonWorkHistoryServiceException;
import com.ginkgocap.parasol.person.model.PersonWorkHistory;

/**
 * 操作人脉工作经历接口定义
 * 
 */
public interface PersonWorkHistoryService {

	/**
	 * 添加人脉工作经历
	 * @param list 
	 * @param id
	 * @return List
	 * @throws PersonWorkHistoryServiceException
	 */
	public List<PersonWorkHistory> createPersonWorkHistoryByList(List<PersonWorkHistory> list,Long id) throws PersonWorkHistoryServiceException; 
	
	/**
	 * 根据id获取人脉工作经历ID列表
	 * @param id 
	 * @return list
	 * @throws PersonWorkHistoryServiceException
	 */
	public List<Long> getIdList(Long id) throws PersonWorkHistoryServiceException;
	/**
	 * 根据Id列表获取人脉工作经历列表
	 * @param ids 
	 * @return list
	 * @throws PersonWorkHistoryServiceException
	 */
	public List<PersonWorkHistory> getIdList(List<Long> ids) throws PersonWorkHistoryServiceException;

	
	/**
	 * 根据id列表批量删除人脉工作经历
	 * @param list 
	 * @return boolean
	 * @throws PersonWorkHistoryServiceException
	 */
	public boolean realDeletePersonWorkHistoryList(List<Long> list) throws PersonWorkHistoryServiceException;
	
}
