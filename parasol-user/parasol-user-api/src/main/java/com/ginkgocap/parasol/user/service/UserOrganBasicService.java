package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserOrganBasicServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserOrganBasic;

/**
 * 操作用户基本信息接口定义
 * 
 */
public interface UserOrganBasicService {

	/**
	 * 创建用户基本信息
	 * @param userOrganBasic 
	 * @return Long
	 * @throws UserOrganBasicServiceException
	 */
	public Long createUserOrganBasic(UserOrganBasic userOrganBasic) throws UserOrganBasicServiceException ,UserLoginRegisterServiceException; 
	

	/**
	 * 修改用户基本信息
	 * @param userOrganBasic
	 * @return boolean
	 * @throws UserOrganBasicServiceException 
	 */
	public boolean updateUserOrganBasic(UserOrganBasic userOrganBasic) throws UserOrganBasicServiceException,UserLoginRegisterServiceException; 

	/**
	 * 根据id获取用户基本信息
	 * @param userId
	 * @return UserOrganBasic
	 * @throws UserOrganBasicServiceException 
	 */
	public UserOrganBasic getUserOrganBasic(Long userId) throws UserOrganBasicServiceException; 
	
	
	/**
	 * 根据userId列表获取用户基本信息列表
	 * @param userIds 
	 * @return List
	 * @throws UserOrganBasicServiceException
	 */
	public List<UserOrganBasic> getUserBasecList(List<Long> userIds) throws UserOrganBasicServiceException;	

}
