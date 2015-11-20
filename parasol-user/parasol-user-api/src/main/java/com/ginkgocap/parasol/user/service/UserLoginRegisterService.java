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
	 * @param value
	 * @return
	 * @throws UserLoginRegisterServiceException 
	 */
	public UserLoginRegister getUserLoginRegister(String passport,String value) throws UserLoginRegisterServiceException; 
	
	
	/**
	 * 修改密码
	 * @param code
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	public boolean updatePassword(Integer id,String password) throws UserLoginRegisterServiceException;
	
	/**
	 * 判断用户是否存在
	 * @param code
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	public boolean passportIsExist(String passport,String value) throws UserLoginRegisterServiceException;
	

	

}
