package com.ginkgocap.parasol.tags.service;

import java.util.List;

import com.ginkgocap.parasol.tags.exception.TagServiceException;
import com.ginkgocap.parasol.tags.exception.TagSourceServiceException;
import com.ginkgocap.parasol.tags.model.TagSource;

/**
 * 
 * @author allenshen
 * @date 2015年12月23日
 * @time 下午12:00:36
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface TagSourceService {

	/**
	 * 创建TagSource，比如创建一篇文章下边的一个标签
	 * 
	 * @param source
	 * @return
	 * @throws TagSourceServiceException
	 */
	public Long createTagSource(TagSource source) throws TagSourceServiceException;

	/**
	 * 删除TagSource，比如删除一篇文章下边的一个标签
	 * 
	 * @param appId
	 * @param tagSourceId
	 * @return
	 * @throws TagSourceServiceException
	 */
	public boolean removeTagSource(Long appId, long tagSourceId) throws TagSourceServiceException;

	/**
	 * 查询一个TagSource
	 * 
	 * @param appId
	 * @param tagSourceId
	 * @return
	 * @throws TagSourceServiceException
	 */
	public TagSource getTagSource(Long appId, long tagSourceId) throws TagSourceServiceException;

	/**
	 * 查询一个资源下边的所有标签，比如查询一篇文章下边的所有标签。
	 * 
	 * @param appId
	 * @param sourceId
	 * @param sourceType
	 * @return
	 * @throws TagSourceServiceException
	 */
	public List<TagSource> getTagSourcesByAppIdSourceIdSourceType(Long appId, Long sourceId, Integer sourceType) throws TagSourceServiceException;

	/**
	 * 统计一个资源下边的标签数量。
	 * 
	 * @param appId
	 * @param sourceId
	 * @param sourceType
	 * @return
	 * @throws TagSourceServiceException
	 */
	public Integer countTagSourcesByAppIdSourceIdSourceType(Long appId, Long sourceId, Integer sourceType) throws TagSourceServiceException;

	/**
	 * 根据一个应用的的TagId查找SourceId列表 比如：根据知识的“智能硬件”标签查找TagSource对象。
	 * 
	 * @param appId
	 * @param tagId
	 * @return
	 * @throws TagSourceServiceException
	 */
	public List<TagSource> getTagSourcesByAppIdTagId(Long appId, Long tagId, int iStart, int iCount) throws TagSourceServiceException;

	/**
	 * 根据一个应用的的TagId查找SourceId列表 比如：根据知识的“智能硬件”标签查找TagSource对象。
	 * 
	 * @param appId
	 * @param tagId
	 * @return
	 * @throws TagSourceServiceException
	 */
	public Integer countTagSourcesByAppIdTagId(Long appId, Long tagId) throws TagSourceServiceException;

	/**
	 * 删除所有使用指定Tag的TagSource
	 * 
	 * @param appId
	 * @param tagId
	 * @return
	 * @throws TagSourceServiceException
	 */
	public boolean removeTagSourcesByTagId(Long appId, Long tagId) throws TagSourceServiceException;
}