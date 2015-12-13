package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserDefinedServiceException;
import com.ginkgocap.parasol.user.model.UserDefined;

/**
 * 操作用户自定义接口定义
 * 
 */
public interface UserDefinedService {

	/**
	 * 添加用户自定义
	 * @param list 
	 * @param userId
	 * @return List
	 * @throws UserDefinedServiceException
	 */
	public List<UserDefined> createUserDefinedByList(List<UserDefined> list,Long userId) throws UserDefinedServiceException; 
	
	/**
	 * 根据userId获取用户自定义ID列表
	 * @param userId 
	 * @return list
	 * @throws UserDefinedServiceException
	 */
	public List<Long> getIdList(Long userId) throws UserDefinedServiceException;
	/**
	 * 根据Id列表获取用户自定义列表
	 * @param ids 
	 * @return list
	 * @throws UserDefinedServiceException
	 */
	public List<UserDefined> getIdList(List<Long> ids) throws UserDefinedServiceException;

	
	/**
	 * 根据id列表批量删除用户自定义
	 * @param list 
	 * @return boolean
	 * @throws UserDefinedServiceException
	 */
	public boolean realDeleteUserDefinedList(List<Long> list) throws UserDefinedServiceException;
	
}
