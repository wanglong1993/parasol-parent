package com.ginkgocap.parasol.comment.service;

import java.util.List;

import com.ginkgocap.parasol.comment.exception.CommentTypeServiceException;
import com.ginkgocap.parasol.comment.model.CommentType;

/**
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午1:10:30
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface CommentTypeService {

	/**
	 * 创建指定应用的关联类型
	 * 
	 * @param appId
	 *            应用Id
	 * @param commentType
	 *            关联类型
	 * @param comment
	 * @return
	 * @throws CommenterviceException
	 */
	public Long createCommentType(Long appId, CommentType commentType) throws CommentTypeServiceException;

	/**
	 * 删除一个应用的关联分类
	 * 小心使用
	 * @param appId
	 * @param userId
	 * @param commentTypeId
	 * @return
	 * @throws CommenterviceException
	 */
	public boolean removeCommentType(Long appId, Long commentTypeId) throws CommentTypeServiceException;

	/**
	 * 更新一个应用的关联分类
	 * @param appId
	 * @param commentType
	 * @return
	 * @throws CommentTypeServiceException
	 */
	public boolean updateCommentType(Long appId, CommentType commentType) throws CommentTypeServiceException;

	/**
	 * 查询一个关联分类
	 * 
	 * @param commentId
	 * @return
	 * @throws CommenterviceException
	 */
	public CommentType getCommentType(Long appId, Long commentTypeId) throws CommentTypeServiceException;

	/**
	 * 查询一个应用的关联分类列表
	 */
	public List<CommentType> getCommentTypessByAppId(Long appId) throws CommentTypeServiceException;

	/**
	 * 查询一个应用的关联分类列表数量
	 * @param appId
	 * @return
	 * @throws CommentTypeServiceException
	 */
	public int countCommentTypessByAppId(Long appId) throws CommentTypeServiceException;
	
	
	/**
	 * 通过名字查找一个分类对象
	 * @param appId
	 * @param name
	 * @return
	 * @throws CommentTypeServiceException
	 */
	public CommentType getCommentTypeByName(Long appId, String name) throws CommentTypeServiceException;



}
