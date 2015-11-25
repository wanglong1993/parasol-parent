package com.ginkgocap.parasol.user.service;

import java.io.Serializable;
import java.util.List;

import com.ginkgocap.parasol.user.exception.UserInterestIndustryServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserInterestIndustry;

/**
 * 操作用户感兴趣接口定义
 * 
 */
public interface UserInterestIndustryService {

	/**
	 * 添加感兴趣行业
	 * @param userInterestIndustry 
	 * @return Long
	 * @throws UserLoginRegisterServiceException
	 */
	public List<UserInterestIndustry> createUserInterestIndustryByList(List<UserInterestIndustry> list,Long userId) throws UserInterestIndustryServiceException; 
	
	
	/**
	 * 根据行业id获取userId分页列表
	 * @param start 
	 * @param count 
	 * @param firstInterestId
	 * @return List
	 * @throws UserInterestIndustryServiceException
	 */
	public List<Long> getUserIdListByIndustryId(int start,int count,Long firstInterestId) throws UserInterestIndustryServiceException;
	
	/**
	 * 根据用户Id获取感兴趣的id
	 * @param userId 
	 * @return list
	 * @throws UserInterestIndustryServiceException
	 */
	public List<Long> getIdList(Long userId) throws UserInterestIndustryServiceException;

	/**
	 * Id是否存在
	 * @param id 
	 * @return boolean
	 * @throws UserInterestIndustryServiceException
	 */
	public boolean idExists(Long id) throws UserInterestIndustryServiceException;
	
	/**
	 * 根据id列表批量删除用户感兴趣行业
	 * @param list 
	 * @return boolean
	 * @throws UserInterestIndustryServiceException
	 */
	public boolean realDeleteUserInterestIndustryList(List<Long> list) throws UserInterestIndustryServiceException;
	
}
