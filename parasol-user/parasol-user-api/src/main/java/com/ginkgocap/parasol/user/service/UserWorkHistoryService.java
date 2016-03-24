package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserWorkHistoryServiceException;
import com.ginkgocap.parasol.user.model.UserWorkHistory;

/**
 * 操作用户工作经历接口定义
 * 
 */
public interface UserWorkHistoryService {

	/**
	 * 添加用户工作经历
	 * @param list 
	 * @param id
	 * @return List
	 * @throws UserWorkHistoryServiceException
	 */
	public List<UserWorkHistory> createUserWorkHistoryByList(List<UserWorkHistory> list,Long id) throws UserWorkHistoryServiceException; 
	/**
	 * 修改用户工作经历
	 * @param list 
	 * @param id
	 * @return List
	 * @throws UserWorkHistoryServiceException
	 */
	public List<UserWorkHistory> updateUserWorkHistoryByList(List<UserWorkHistory> list,Long id) throws UserWorkHistoryServiceException; 
	
	/**
	 * 根据id获取用户工作经历ID列表
	 * @param id 
	 * @return list
	 * @throws UserWorkHistoryServiceException
	 */
	public List<Long> getIdList(Long id) throws UserWorkHistoryServiceException;
	/**
	 * 根据Id列表获取用户工作经历列表
	 * @param ids 
	 * @return list
	 * @throws UserWorkHistoryServiceException
	 */
	public List<UserWorkHistory> getIdList(List<Long> ids) throws UserWorkHistoryServiceException;

	
	/**
	 * 根据id列表批量删除用户工作经历
	 * @param list 
	 * @return boolean
	 * @throws UserWorkHistoryServiceException
	 */
	public boolean realDeleteUserWorkHistoryList(List<Long> list) throws UserWorkHistoryServiceException;
	
}
