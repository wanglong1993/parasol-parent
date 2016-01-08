package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserExtServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserExt;

/**
 * 操作用户扩展信息接口定义
 * 
 */
public interface UserExtService {

	/**
	 * 创建用户基本信息
	 * @param UserExt 
	 * @return Long
	 * @throws UserExtServiceException
	 */
	public Long createUserExt(UserExt UserExt) throws UserExtServiceException ,UserLoginRegisterServiceException; 
	

	/**
	 * 修改用户基本信息
	 * @param UserExt
	 * @return boolean
	 * @throws UserExtServiceException 
	 */
	public boolean updateUserExt(UserExt UserExt) throws UserExtServiceException,UserLoginRegisterServiceException; 

	/**
	 * 根据id获取用户基本信息
	 * @param userId
	 * @return UserExt
	 * @throws UserExtServiceException 
	 */
	public UserExt getUserExt(Long userId) throws UserExtServiceException; 
	
	
	/**
	 * 根据userId列表获取用户基本信息列表
	 * @param userIds 
	 * @return List
	 * @throws UserExtServiceException
	 */
	public List<UserExt> getUserExtList(List<Long> userIds) throws UserExtServiceException;	
	/**
	 * 根据用户所在县id获取用户列表
	 * @param start 
	 * @param count
	 * @param countyId  
	 * @return List
	 * @throws UserExtServiceException
	 */
	public List<UserExt> getUserExtListByCountryId(int start,int count,Long countyId) throws UserExtServiceException;	
	/**
	 * 根据用户所在地区省id获取用户列表
	 * @param start 
	 * @param count 
	 * @param ProvinceId 
	 * @return List
	 * @throws UserExtServiceException
	 */
	public List<UserExt> getUserExtListByProvinceId(int start,int count,Long ProvinceId) throws UserExtServiceException;
	/**
	 * 根据第三级行业ID获取用户列表
	 * @param start 
	 * @param count 
	 * @param ProvinceId 
	 * @return List
	 * @throws UserExtServiceException
	 */
	public List<UserExt> getUserListByThirdIndustryId(int start,int count,Long thirdIndustryId) throws UserExtServiceException;
	/**
	 * 根据id真删除用户基本信息
	 * @param id
	 * @return Boolean
	 * @throws UserLoginRegisterServiceException
	 */
	public Boolean realDeleteUserExt(Long id) throws UserExtServiceException;	

}
