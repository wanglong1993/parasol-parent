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
	 * @param passport
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	public boolean passportIsExist(String passport) throws UserLoginRegisterServiceException;
	/**
	 * 根据passport获取用户id
	 * @param passport
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	public Long getId(String passport) throws UserLoginRegisterServiceException;
	
	/**
	 * 修改用户登录IP和登录时间
	 * @param id
	 * @param ip
	 * @param utime 登录时间
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	public boolean updateIpAndLoginTime(Long id,String ip) throws UserLoginRegisterServiceException;
	/**
	 * 生成salt
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	public String setSalt() throws UserLoginRegisterServiceException;
	/**
	 * Sha256加密密码
	 * @param password
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	public String setSha256Hash(String salt,String password) throws UserLoginRegisterServiceException;
	/**
	 * 真删除登录注册用户
	 * @param id
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	public Boolean realDeleteUserLoginRegister(Long id) throws UserLoginRegisterServiceException;
	/**
	 * 假删除登录注册用户
	 * @param id
	 * @return
	 * @throws UserLoginRegisterServiceException
	 */
	public Boolean fakeDeleteUserLoginRegister(Long id) throws UserLoginRegisterServiceException;
	

	

}
