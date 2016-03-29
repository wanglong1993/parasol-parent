package com.ginkgocap.parasol.mapping.service;

import com.ginkgocap.parasol.mapping.enumtype.MappingType;
import com.ginkgocap.parasol.mapping.exception.MappingServiceException;



/**
 * 如果要是分库本Service用UserId
 * 
 * @author allenshen
 * @date 2015年12月23日
 * @time 下午12:00:36
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface MappingService {

	/**
	 * 创建一个Mapping
	 * 
	 * @param userId
	 * @param tag
	 * @return
	 * @throws MappingServiceException
	 */
	public Long saveMapping(Long openId, Long uId, MappingType type) throws MappingServiceException;

	/**
	 * 根据开放平台的id，找万能插座的ID（universal socket id）
	 * 
	 * @param openId 开放平台的Id
	 * @param type
	 * @return 万能插座的Id
	 * @throws MappingServiceException
	 */
	public Long getUid(Long openId, MappingType type) throws MappingServiceException;

	/**
	 * 根据万能插座的ID（universal socket id），找开放平台的id
	 * 
	 * @param uId 万能插座的id (universal socket id)
	 * @param type
	 * @return 返回开放平台的Id
	 * @throws MappingServiceException
	 */
	public Long getOpenId(Long uId, MappingType type) throws MappingServiceException;
}
