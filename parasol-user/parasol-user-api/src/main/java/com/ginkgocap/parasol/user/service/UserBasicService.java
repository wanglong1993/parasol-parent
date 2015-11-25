package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserBasicServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserBasic;

/**
 * 操作用户基本信息接口定义
 * 
 */
public interface UserBasicService {

	/**
	 * 创建用户基本信息
	 * @param userBasic 
	 * @return Long
	 * @throws UserBasicServiceException
	 */
	public Long createUserBasic(UserBasic userBasic) throws UserBasicServiceException ,UserLoginRegisterServiceException; 
	

	/**
	 * 修改用户基本信息
	 * @param userBasic
	 * @return boolean
	 * @throws UserBasicServiceException 
	 */
	public boolean updateUserBasic(UserBasic userBasic) throws UserBasicServiceException,UserLoginRegisterServiceException; 

	/**
	 * 根据id获取用户基本信息
	 * @param userId
	 * @return UserBasic
	 * @throws UserBasicServiceException 
	 */
	public UserBasic getUserBasic(Long userId) throws UserBasicServiceException; 
	
	
	/**
	 * 根据userId列表获取用户基本信息列表
	 * @param userIds 
	 * @return List
	 * @throws UserBasicServiceException
	 */
	public List<UserBasic> getUserBasecList(List<Long> userIds) throws UserBasicServiceException;	

}
