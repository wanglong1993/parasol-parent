package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserConfigServiceException;
import com.ginkgocap.parasol.user.model.UserConfig;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;

/**
 * 用户设置
 * 
 */
public interface UserConfigService {

	/**
	 * 创建用户设置
	 * @param userConfig 
	 * @return Long
	 * @throws UserConfigServiceException
	 */
	public Long createUserConfig(UserConfig userConfig) throws UserConfigServiceException,UserLoginRegisterServiceException; 
	

	/**
	 * 修改用户设置
	 * @param userConfig
	 * @return boolean
	 * @throws UserConfigServiceException 
	 */
	public boolean updateUserConfig(UserConfig userConfig) throws UserConfigServiceException,UserLoginRegisterServiceException; 

	/**
	 * 根据id获取用户设置
	 * @param id
	 * @return userConfig
	 * @throws UserConfigServiceException 
	 */
	public UserConfig getUserConfig(Long id) throws UserConfigServiceException; 
	
	/**
	 * 根据id列表获取用户设置列表
	 * @param ids 
	 * @return List
	 * @throws UserConfigServiceException
	 */
	public List<UserConfig> getUserConfig(List<Long> ids) throws UserConfigServiceException;	
	
	/**
	 * 根据id真删除用户设置
	 * @param id
	 * @return Boolean
	 * @throws UserConfigServiceException
	 */
	public Boolean realDeleteUserConfig(Long id) throws UserConfigServiceException;	

}
