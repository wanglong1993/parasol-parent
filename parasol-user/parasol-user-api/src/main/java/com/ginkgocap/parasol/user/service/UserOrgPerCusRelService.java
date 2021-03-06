package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.exception.UserFriendlyServiceException;
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
	 * 修改用户好友人脉组织客户关系
	 * @param userOrgPerCusRel 
	 * @return Long
	 * @throws UserOrgPerCusRelServiceException
	 */
	public boolean updateUserOrgPerCusRelList(List<UserOrgPerCusRel> userOrgPerCusRelList) throws UserOrgPerCusRelServiceException; 
	

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
	 * 获取用户好友
	 * @param userId
	 * @param friendId
	 * @return UserOrgPerCusRel
	 * @throws UserOrgPerCusRelServiceException 
	 */
	public UserOrgPerCusRel getUserOrgPerCusRel(Long userId,Long friendId) throws UserOrgPerCusRelServiceException; 

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
	 * 根据组织好友昵称获取用户我的里面的组织好友列表
	 * @param userId 
	 * @param name
	 * @return List
	 * @throws UserOrgPerCusRelServiceException
	 */
	public List<UserOrgPerCusRel> getOrgFriendlylListByNickname(int start,int count,Long userId,String name) throws UserOrgPerCusRelServiceException;
	/**
	 * 根据userId获取用户通讯录个人和组织好友列表
	 * @param userId 
	 * @return List
	 * @throws UserOrgPerCusRelServiceException
	 */
	public List<UserOrgPerCusRel> getUserAndOrgFriendlyList(int start,int count,Long userId) throws UserOrgPerCusRelServiceException;	
	/**
	 * 根据好友昵称搜索我的里面的个人好友列表
	 * @param userId 
	 * @param name 
	 * @return List
	 * @throws UserOrgPerCusRelServiceException
	 */
	public List<UserOrgPerCusRel> getUserFriendlyListByNickname(int start,int count,Long userId,String name) throws UserOrgPerCusRelServiceException;	
	/**
	 * 删除用户好友
	 * @param friendId
	 * @return Boolean
	 * @throws UserOrgPerCusRelServiceException
	 */
	public Boolean deleteFriendly(Long userId,Long friendId) throws UserOrgPerCusRelServiceException;	
	
	/**
	 * 通过主键删除
	 * @param id
	 * @throws UserOrgPerCusRelServiceException
	 */
	public boolean realDeleteUserOrgPerCusRel(Long id)throws UserOrgPerCusRelServiceException;
	

}
