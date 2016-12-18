package com.ginkgocap.parasol.tags.service;

import java.util.List;

import com.ginkgocap.parasol.tags.exception.TagServiceException;
import com.ginkgocap.parasol.tags.model.Tag;

/**
 * 如果要是分库本Service用UserId
 * 
 * @author allenshen
 * @date 2015年12月23日
 * @time 下午12:00:36
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface TagService {

	/**
	 * 创建一个标签
	 * 
	 * @param userId
	 * @param tag
	 * @return
	 * @throws TagServiceException
	 */
	public Long createTag(Long userId, Tag tag) throws TagServiceException;

	/**
	 * 删除一个标签, Note：要删除使用此标签的资源下边的引用
	 * 
	 * @param userId
	 * @param tagId
	 * @return
	 * @throws TagServiceException
	 */
	public boolean removeTag(Long userId, long tagId) throws TagServiceException;

	/**
	 * 更新标签
	 * 
	 * @param userId
	 * @param tag
	 * @return
	 * @throws TagServiceException
	 */
	public boolean updateTag(Long userId, Tag tag) throws TagServiceException;

	/**
	 * 查询一个标签
	 * 
	 * @param userId
	 * @param id
	 * @return
	 * @throws TagServiceException
	 */
	public Tag getTag(Long userId, Long id) throws TagServiceException;

	/**
	 * 查询标签集合，通过ids
	 * @param userId
	 * @param ids
	 * @return
	 * @throws TagServiceException
	 */
	public List<Tag> getTags(Long userId, List<Long> ids) throws TagServiceException;

	/**
	 * 查找指定用户指定应用指定分类的Tag列表
	 * 
	 * @param userId
	 * @param appId
	 * @param tagType
	 * @return
	 * @throws TagServiceException
	 */
	public List<Tag> getTagsByUserIdAppidTagType(Long userId, Long appId, Long tagType) throws TagServiceException;

	/**
	 * 查找指定用户指定应用指定分类的Tag列表
	 * 
	 * @param appId
	 * @param userId
	 * @param pId
	 * @return
	 * @throws TagServiceException
	 */
	public int countTagsByUserIdAppidTagType(Long userId, Long appId, Long tagType) throws TagServiceException;

	/**
	 * 查找大数据指定分类的Tag列表个数
	 * @param userId
	 * @param appId
	 * @param tagType
	 * @return
	 * @throws TagServiceException
	 */
	public int countDefaultTagsByUserIdAppidTagType(Long userId, Long appId, Long tagType) throws TagServiceException;

	/**
	 * 查找大数据指定分类的Tag列表
	 * @param userId
	 * @param appId
	 * @param tagType
	 * @param start
	 * @param size
	 * @throws TagServiceException
	 * @return
	 */
	public List<Tag> getTagsByUserIdAppidTagTypePage(Long userId, Long appId, Long tagType, int start, int size) throws TagServiceException;
}
