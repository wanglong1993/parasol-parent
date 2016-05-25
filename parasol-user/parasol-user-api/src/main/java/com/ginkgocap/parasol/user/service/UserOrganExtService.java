package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserExtServiceException;
import com.ginkgocap.parasol.user.exception.UserOrganExtServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserOrganExt;

/**
 * 操作用户基本信息接口定义
 * 
 */
public interface UserOrganExtService {

	/**
	 * 创建组织用户基本信息
	 * @param UserOrganExt 
	 * @return Long
	 * @throws UserOrganExtServiceException
	 */
	public Long createUserOrganExt(UserOrganExt UserOrganExt) throws UserOrganExtServiceException ,UserLoginRegisterServiceException; 
	

	/**
	 * 修改组织用户基本信息
	 * @param UserOrganExt
	 * @return boolean
	 * @throws UserOrganExtServiceException 
	 */
	public boolean updateUserOrganExt(UserOrganExt UserOrganExt) throws UserOrganExtServiceException,UserLoginRegisterServiceException; 


	/**
	 * 根据id获取用户基本信息
	 * @param userId
	 * @return UserOrganExt
	 * @throws UserOrganExtServiceException 
	 */
	public UserOrganExt getUserOrganExt(Long userId) throws UserOrganExtServiceException; 
	
	
	/**
	 * 根据userId列表获取用户基本信息列表
	 * @param userIds 
	 * @return List
	 * @throws UserOrganExtServiceException
	 */
	public List<UserOrganExt> getUserOrganExtList(List<Long> userIds) throws UserOrganExtServiceException;	
	
	/**
	 * 根据id真删除用户扩展信息
	 * @param id
	 * @return Boolean
	 * @throws UserOrganExtServiceException
	 */
	public Boolean realDeleteUserOrganExt(Long id) throws UserOrganExtServiceException;	

}
