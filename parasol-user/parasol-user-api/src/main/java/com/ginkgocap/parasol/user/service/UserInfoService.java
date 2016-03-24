package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserInfoServiceException;
import com.ginkgocap.parasol.user.model.UserInfo;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;

/**
 * 个人情况
 * 
 */
public interface UserInfoService {

	/**
	 * 创建用户个人情况
	 * @param userInfo 
	 * @return Long
	 * @throws UserInfoServiceException
	 */
	public Long createUserInfo(UserInfo userInfo) throws UserInfoServiceException,UserLoginRegisterServiceException; 
	

	/**
	 * 修改用户个人情况
	 * @param userInfo
	 * @return boolean
	 * @throws UserInfoServiceException 
	 */
	public boolean updateUserInfo(UserInfo userInfo) throws UserInfoServiceException,UserLoginRegisterServiceException; 

	/**
	 * 根据id获取用户个人情况
	 * @param id
	 * @return userInfo
	 * @throws UserInfoServiceException 
	 */
	public UserInfo getUserInfo(Long id) throws UserInfoServiceException; 
	
	/**
	 * 根据id列表获取用户个人情况列表
	 * @param ids 
	 * @return List
	 * @throws UserInfoServiceException
	 */
	public List<UserInfo> getUserInfo(List<Long> ids) throws UserInfoServiceException;	
	
	/**
	 * 根据id真删除用户个人情况
	 * @param id
	 * @return Boolean
	 * @throws UserInfoServiceException
	 */
	public Boolean realDeleteUserInfo(Long id) throws UserInfoServiceException;	

}
