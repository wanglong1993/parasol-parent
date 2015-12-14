package com.ginkgocap.parasol.user.service;

import java.util.List;
import java.util.Map;

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
	 * @return Long
	 * @throws UserLoginRegisterServiceException
	 */
	public Long createUserLoginRegister(UserLoginRegister userLoginRegister) throws UserLoginRegisterServiceException; 
	/**
	 * 修改
	 * @param userLoginRegister 
	 * @return Long
	 * @throws UserLoginRegisterServiceException
	 */
	public Boolean updataUserLoginRegister(UserLoginRegister userLoginRegister) throws UserLoginRegisterServiceException; 
	

	/**
	 * 根据passport获取用户登录注册信息
	 * @param passport
	 * @return UserLoginRegister
	 * @throws UserLoginRegisterServiceException 
	 */
	public UserLoginRegister getUserLoginRegister(String passport) throws UserLoginRegisterServiceException; 

	/**
	 * 根据id获取用户登录注册信息
	 * @param id
	 * @return UserLoginRegister
	 * @throws UserLoginRegisterServiceException 
	 */
	public UserLoginRegister getUserLoginRegister(Long id) throws UserLoginRegisterServiceException; 
	
	
	/**
	 * 修改密码
	 * @param id
	 * @param password
	 * @return boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public boolean updatePassword(Long id,String password) throws UserLoginRegisterServiceException;
	
	/**
	 * 根据passport判断用户是否存在
	 * @param passport
	 * @return boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public boolean passportIsExist(String passport) throws UserLoginRegisterServiceException;
	/**
	 * 根据passport获取用户id
	 * @param passport
	 * @return Long
	 * @throws UserLoginRegisterServiceException
	 */
	public Long getId(String passport) throws UserLoginRegisterServiceException;
	
	/**
	 * 修改用户登录IP和登录时间
	 * @param id
	 * @param ip
	 * @return boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public boolean updateIpAndLoginTime(Long id,String ip) throws UserLoginRegisterServiceException;
	/**
	 * 生成salt
	 * @return String
	 * @throws UserLoginRegisterServiceException
	 */
	public String setSalt() throws UserLoginRegisterServiceException;
	/**
	 * Sha256加密密码
	 * @param password
	 * @return String
	 * @throws UserLoginRegisterServiceException
	 */
	public String setSha256Hash(String salt,String password) throws UserLoginRegisterServiceException;
	/**
	 * 根据id真删除登录注册用户
	 * @param id
	 * @return Boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public Boolean realDeleteUserLoginRegister(Long id) throws UserLoginRegisterServiceException;
	/**
	 * 根据id列表批量删除登录注册用户
	 * @param  list
	 * @return Boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public boolean realDeleteUserLoginRegisterList(List<Long> list) throws UserLoginRegisterServiceException;	
	/**
	 * 根据id假删除登录注册用户
	 * @param id
	 * @return Boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public Boolean fakeDeleteUserLoginRegister(Long id) throws UserLoginRegisterServiceException;
	/**
	 * 根据id数组批量假删除登录注册用户
	 * @param  list
	 * @return Boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public int fakeDeleteUserLoginRegister(List<Long> list) throws UserLoginRegisterServiceException;
	/**
	 * 绑定手机号
	 * @param  mobile
	 * @param  id
	 * @return Boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public Boolean setMobile(String mobile,Long id) throws UserLoginRegisterServiceException;
	/**
	 * 绑定邮箱
	 * @param  email
	 * @param  id
	 * @return Boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public Boolean setEmail(String email,Long id) throws UserLoginRegisterServiceException;
	/**
	 * 发送验证码,找回密码
	 * @param  mobile
	 * @return Boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public String sendIdentifyingCode(String mobile)throws UserLoginRegisterServiceException;
	/**
	 * 获取验证码
	 * @param  mobile
	 * @return Boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public String getIdentifyingCode(String mobile)throws UserLoginRegisterServiceException;
	/**
	 * 是否手机号
	 * @param  mobile
	 * @return Boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public  boolean isMobileNo(String mobile);
	/**
	 * 发送邮件
	 * @param mailTo
	 * @param type 发送邮件类型,1.邮箱注册,2.找回密码,3.修改密码,4.绑定邮箱
	 * @param map
	 * @throws UserLoginRegisterServiceException
	 */
	public boolean sendEmail(String mailTo, int type,Map<String, Object> map) throws UserLoginRegisterServiceException;
	/**
	 * 获取邮箱地址是否缓存
	 * @param email
	 * @throws UserLoginRegisterServiceException
	 */
	public boolean getEmail(String email) throws UserLoginRegisterServiceException;

	

}
