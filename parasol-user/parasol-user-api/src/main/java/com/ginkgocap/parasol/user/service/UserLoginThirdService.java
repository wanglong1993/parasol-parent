package com.ginkgocap.parasol.user.service;

import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginThirdServiceException;
import com.ginkgocap.parasol.user.model.UserLoginThird;

/**
 * 第三方登录
 */
public interface UserLoginThirdService {
	
	/**
	 * 获取qq或者微博的第三方登录访问地址
	 * @param type 1为qq,2为sina微博
	 * @return String 
	 */
	public String  getLoginThirdUrl(int type) throws UserLoginThirdServiceException;
	
	/**
	 * 获取qq或者微博的第三方登录访问地址
	 * @param logi_type
	 * @param access_token
	 * @param nickName
	 * @param headpic
	 * @return String 
	 */
	public String  saveUserLoginThird(int login_type,String access_token, String nikeName,String headpic) throws UserLoginThirdServiceException;

	/**
	 * 创建第三方登录
	 * 
	 * @param userLoginThird
	 * @return
	 */
	public Long  saveUserLoginThird(UserLoginThird userLoginThird) throws UserLoginThirdServiceException;
	/**
	 * 修改第三方登录
	 * 
	 * @param userLoginThird
	 * @return
	 */
	public Boolean  updateUserLoginThird(UserLoginThird userLoginThird)throws UserLoginThirdServiceException;	

	/**
	 * 通过openId检测账号是否绑定
	 * 
	 * @param openId
	 * @return
	 */
	public UserLoginThird getUserLoginThirdByOpenId(String openId) throws UserLoginThirdServiceException;

	/**
	 * 通过openId修改accesstoken值
	 * 
	 * @param openId
	 * @param accesstoken
	 * @return Boolean
	 */
	public Boolean updateAccesstoken(String openId, String accesstoken)throws UserLoginThirdServiceException;


	/**
	 * 功能描述：判断是否绑定第三方
	 * 
	 * @param openId
	 * @param loginType
	 * @return map
	 * @throws UserLoginThirdServiceException 
	 */
	boolean isBinding(String openId, Byte loginType) throws UserLoginThirdServiceException;


	/**
	 * 
	 * @param user
	 * @return
	 */
	//User getPersistenceUser(User user);
	
	/**
	 * 根据openId和登录类型查找用户是否绑定
	 * @param openId
	 * @param loginType
	 * @return UserLoginThird
	 */
	public UserLoginThird selectUserByOpenId(String openId, int loginType) throws UserLoginThirdServiceException;

	/**
	 * 根据userId查找用户是否绑定
	 * @param userId
	 * @return UserLoginThird
	 */
	public UserLoginThird getUserLoginThirdByUserId(Long userId) throws UserLoginThirdServiceException;
	
	/**
	 * 根据id查找第三方登录对象
	 * @param id
	 * @return UserLoginThird
	 */	
	public UserLoginThird getUserLoginThirdById(Long id) throws UserLoginThirdServiceException;
	/**
	 * 根据id真删除第三方登录
	 * @param id
	 * @return Boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public Boolean realDeleteUserLoginThird(Long id) throws UserLoginRegisterServiceException;
}
