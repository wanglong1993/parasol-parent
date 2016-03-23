package com.ginkgocap.parasol.comment.service;

import java.util.List;

import com.ginkgocap.parasol.comment.exception.CommentServiceException;
import com.ginkgocap.parasol.comment.model.Comment;

/**
 * 操作Comment的接口定义
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:46:44
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface CommentService {

	/**
	 * 创建对象评论
	 * 
	 * @param appId
	 *            , 应用的AppId
	 * @param userId
	 *            , 应用的用户Id
	 * @param comment
	 *            对象评论对象
	 * @return
	 * @throws CommentServiceException
	 */
	public Long createComment(Long appId, Long userId, Comment comment) throws CommentServiceException;

	/**
	 * 删除对象评论
	 * 
	 * @param appId
	 * @param userId
	 * @param commentId
	 * @return
	 * @throws CommentServiceException
	 */
	public boolean removeComment(Long appId, Long userId, Long commentId) throws CommentServiceException;

	/**
	 * 查询一个评论
	 * 
	 * @param appId
	 * @param userId
	 * @param commentId
	 * @return
	 * @throws CommentServiceException
	 */
	public Comment getComment(Long appId, Long userId, Long commentId) throws CommentServiceException;

	/**
	 * 统计一个对象有多少评论
	 * 
	 * @param appId
	 * @param sourceTypeId
	 * @param sourceId
	 * @return
	 * @throws CommentServiceException
	 */
	public int countComment(Long appId, Long sourceTypeId, Long sourceId) throws CommentServiceException;

	/**
	 * 查询一个对象下边的评论列表
	 * 
	 * @param appId
	 *            应用ID
	 * @param sourceTypeId
	 *            对象的类型
	 * @param sourceId
	 *            对象Id
	 * @param start
	 *            开始位置
	 * @param count
	 *            数量
	 * @return
	 * @throws CommentServiceException
	 */
	public List<Comment> getComments(Long appId, Long sourceTypeId, Long sourceId, Integer start, Integer count) throws CommentServiceException;

	/**
	 * 查询一个对象下边的评论列表，以一个评论的ID为位置向前，或者向后取多少，不包括评论ID的位置。
	 * 
	 * @param appId
	 * @param sourceTypeId
	 * @param sourceId
	 * @param mindId
	 * @param count
	 * @param forward
	 * @return
	 * @throws CommentServiceException
	 */
	public List<Comment> getCommentsByMinId(Long appId, Long sourceTypeId, Long sourceId, Long mindId, Integer count, Boolean forward) throws CommentServiceException;

}
