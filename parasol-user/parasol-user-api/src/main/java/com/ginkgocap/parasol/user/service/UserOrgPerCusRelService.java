package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserOrgPerCusRelServiceException;
import com.ginkgocap.parasol.user.model.UserOrgPerCusRel;

/**
 * 操作好友人脉组织客户关系接口定义
 * 
 */
public interface UserOrgPerCusRelService {

	/**
	 * 添加用户好友人脉组织客户关系
	 * @param userOrgPerCusRel 
	 * @return Long
	 * @throws UserOrgPerCusRelServiceException
	 */
	public Long createUserOrgPerCusRel(UserOrgPerCusRel userOrgPerCusRel) throws UserOrgPerCusRelServiceException; 
	

	/**
	 * 修改用户好友备注名
	 * @param userId
	 * @param friendId
	 * @param name
	 * @return boolean
	 * @throws UserOrgPerCusRelServiceException 
	 */
	public boolean updateName(Long userId,Long friendId,String name) throws UserOrgPerCusRelServiceException; 

	/**
	 * 根据userId获取用户我的里面的个人好友列表
	 * @param userId 
	 * @return List
	 * @throws UserOrgPerCusRelServiceException
	 */
	public List<UserOrgPerCusRel> getUserFriendlyList(int start,int count,Long userId) throws UserOrgPerCusRelServiceException;	
	/**
	 * 根据userId获取用户我的里面的组织好友列表
	 * @param userId 
	 * @return List
	 * @throws UserOrgPerCusRelServiceException
	 */
	public List<UserOrgPerCusRel> getOrgFriendlylList(int start,int count,Long userId) throws UserOrgPerCusRelServiceException;
	/**
	 * 根据userId获取用户通讯录个人和组织好友列表
	 * @param userId 
	 * @return List
	 * @throws UserOrgPerCusRelServiceException
	 */
	public List<UserOrgPerCusRel> getUserAndOrgFriendlyList(int start,int count,Long userId) throws UserOrgPerCusRelServiceException;	
	/**
	 * 删除用户好友
	 * @param friendId
	 * @return Boolean
	 * @throws UserOrgPerCusRelServiceException
	 */
	public Boolean deleteFriendly(Long friendId) throws UserOrgPerCusRelServiceException;	
	

}
