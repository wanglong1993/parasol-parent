package com.ginkgocap.parasol.user.service;

import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserLoginRegister;

/**
 * 操作用户登录注册接口定义
 * 
 */
public interface UserLoginRegisterService {

	/**
	 * 注册
	 * @param userLoginRegister
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	public Long createUserLoginRegister(UserLoginRegister userLoginRegister) throws UserLoginRegisterServiceException; 
	

	/**
	 * 根据passport获取用户登录注册信息
	 * @param passport
	 * @return
	 * @throws UserLoginRegisterServiceException 
	 */
	public UserLoginRegister getUserLoginRegister(String passport) throws UserLoginRegisterServiceException; 

	/**
	 * 根据id获取用户登录注册信息
	 * @param id
	 * @return
	 * @throws UserLoginRegisterServiceException 
	 */
	public UserLoginRegister getUserLoginRegister(Long id) throws UserLoginRegisterServiceException; 
	
	
	/**
	 * 修改密码
	 * @param id
	 * @param password
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	public boolean updatePassword(Long id,String password) throws UserLoginRegisterServiceException;
	
	/**
	 * 根据passport判断用户是否存在
	 * @param mapping_region
	 * @param passport
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	public boolean passportIsExist(String mapping_region,String passport) throws UserLoginRegisterServiceException;
	

	

}
