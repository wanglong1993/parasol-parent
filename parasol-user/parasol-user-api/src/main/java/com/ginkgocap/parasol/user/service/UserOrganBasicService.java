package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserOrganBasicServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.exception.UserOrganExtServiceException;
import com.ginkgocap.parasol.user.model.UserOrganBasic;

/**
 * 操作用户基本信息接口定义
 * 
 */
public interface UserOrganBasicService {

	/**
	 * 创建组织用户基本信息
	 * @param userOrganBasic 
	 * @return Long
	 * @throws UserOrganBasicServiceException
	 */
	public Long createUserOrganBasic(UserOrganBasic userOrganBasic) throws UserOrganBasicServiceException ,UserLoginRegisterServiceException; 
	

	/**
	 * 修改组织用户基本信息
	 * @param userOrganBasic
	 * @return boolean
	 * @throws UserOrganBasicServiceException 
	 */
	public boolean updateUserOrganBasic(UserOrganBasic userOrganBasic) throws UserOrganBasicServiceException,UserLoginRegisterServiceException; 

	/**
	 * 审核组织
	 * @param id
	 * @param auth
	 * @return boolean
	 * @throws UserOrganBasicServiceException 
	 */
	public boolean updateAuth(Long userId, Byte auth) throws UserOrganBasicServiceException,UserLoginRegisterServiceException; 
	/**
	 * 修改组织状态
	 * @param id
	 * @param status
	 * @return boolean
	 * @throws UserOrganBasicServiceException 
	 */
	public boolean updateStatus(Long userId, Byte status) throws UserOrganBasicServiceException,UserLoginRegisterServiceException; 

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
	public List<UserOrganBasic> getUserOrganBasecList(List<Long> userIds) throws UserOrganBasicServiceException;
	
	/**
	 * 根据id真删除组织用户扩展信息
	 * @param id
	 * @return Boolean
	 * @throws UserOrganBasicServiceException
	 */
	public Boolean realDeleteUserOrganBasic(Long id) throws UserOrganBasicServiceException;		

}
