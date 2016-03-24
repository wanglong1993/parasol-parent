package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserEducationHistoryServiceException;
import com.ginkgocap.parasol.user.model.UserEducationHistory;

/**
 * 操作用户教育经历接口定义
 * 
 */
public interface UserEducationHistoryService {

	/**
	 * 添加用户教育经历
	 * @param list 
	 * @param id
	 * @return List
	 * @throws UserEducationHistoryServiceException
	 */
	public List<UserEducationHistory> createUserEducationHistoryByList(List<UserEducationHistory> list,Long id) throws UserEducationHistoryServiceException; 
	/**
	 * 修改用户教育经历
	 * @param list 
	 * @param id
	 * @return List
	 * @throws UserEducationHistoryServiceException
	 */
	public List<UserEducationHistory> updateUserEducationHistoryByList(List<UserEducationHistory> list,Long id) throws UserEducationHistoryServiceException; 
	
	/**
	 * 根据id获取用户教育经历ID列表
	 * @param id 
	 * @return list
	 * @throws UserEducationHistoryServiceException
	 */
	public List<Long> getIdList(Long id) throws UserEducationHistoryServiceException;
	/**
	 * 根据Id列表获取用户教育经历列表
	 * @param ids 
	 * @return list
	 * @throws UserEducationHistoryServiceException
	 */
	public List<UserEducationHistory> getIdList(List<Long> ids) throws UserEducationHistoryServiceException;

	
	/**
	 * 根据id列表批量删除用户教育经历
	 * @param list 
	 * @return boolean
	 * @throws UserEducationHistoryServiceException
	 */
	public boolean realDeleteUserEducationHistoryList(List<Long> list) throws UserEducationHistoryServiceException;
	
}
