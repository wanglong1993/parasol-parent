package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserContactWayServiceException;
import com.ginkgocap.parasol.user.model.UserContact;

/**
 * 操作用户联系方式接口定义
 * 
 */
public interface UserContactWayService {

	/**
	 * 创建用户联系方式
	 * @param userContactWay 
	 * @return Long
	 * @throws UserContactWayServiceException
	 */
	public Long createUserContactWay(UserContact userContactWay) throws UserContactWayServiceException; 
	

	/**
	 * 修改用户联系方式
	 * @param userContactWay
	 * @return boolean
	 * @throws UserContactWayServiceException 
	 */
	public boolean updateUserContactWay(UserContact userContactWay) throws UserContactWayServiceException; 

	/**
	 * 根据id获取用户联系方式
	 * @param id
	 * @return userContactWay
	 * @throws UserContactWayServiceException 
	 */
	public UserContact getUserContactWay(Long id) throws UserContactWayServiceException; 
	
	/**
	 * 根据id列表获取用户联系方式列表
	 * @param ids 
	 * @return List
	 * @throws UserContactWayServiceException
	 */
	public List<UserContact> getUserContactWayList(List<Long> ids) throws UserContactWayServiceException;	
	
	/**
	 * 根据id真删除用户联系方式
	 * @param id
	 * @return Boolean
	 * @throws UserContactWayServiceException
	 */
	public Boolean realDeleteUserContactWay(Long id) throws UserContactWayServiceException;	

}
