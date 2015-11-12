package com.ginkgocap.parasol.user.service;

import com.ginkgocap.parasol.user.exception.UserServiceException;
import com.ginkgocap.parasol.user.model.UserLoginRegister;

/**
 * 操作用户接口定义
 * 
 */
public interface UserService {

	/**
	 * 注册
	 * @param userLoginRegister
	 * @return
	 * @throws UserServiceException
	 */
	public Long createUser(UserLoginRegister userLoginRegister) throws UserServiceException; 
	

	/**
	 * 根据passport获取用户登录注册信息
	 * @param passport
	 * @return
	 * @throws UserServiceException 
	 */
	public UserLoginRegister getUser(String passport) throws UserServiceException; 
	
	
	/**
	 * 修改密码
	 * @param code
	 * @return
	 * @throws UserServiceException
	 */
	public boolean updatePassword(Integer id,String password) throws UserServiceException;
	

	

}
