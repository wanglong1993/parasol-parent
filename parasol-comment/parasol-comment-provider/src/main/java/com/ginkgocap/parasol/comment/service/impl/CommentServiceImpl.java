package com.ginkgocap.parasol.comment.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.comment.exception.CommentServiceException;
import com.ginkgocap.parasol.comment.exception.CommentTypeServiceException;
import com.ginkgocap.parasol.comment.model.Comment;
import com.ginkgocap.parasol.comment.model.CommentType;
import com.ginkgocap.parasol.comment.service.CommentService;
import com.ginkgocap.parasol.comment.service.CommentTypeService;
import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午4:26:56
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("commentService")
public class CommentServiceImpl extends BaseService<Comment> implements CommentService {
	private static Logger logger = Logger.getLogger(CommentServiceImpl.class);
	private final static int LEN_NAME = 50;
	private final static int LEN_ASSOCIATES = 500;

	private static final int PAGESIZE = 200;

	private static String LIST_COMMENT_ID_APPID_SOURCETYPEID_SOURCEID = "List_Comment_Id_AppId_SourceTypeId_SourceId"; // 查询一个应用的分类根收藏夹

	@Autowired
	private CommentTypeService commentTypeService;

	@Override
	public Long createComment(Long appId, Long userId, Comment comment) throws CommentServiceException {
		// 1.check input parameters(appId, typeId, name，userId)
		ServiceError.assertCommentForComment(comment);
		// 2.check appId
		ServiceError.assertAppIdForComment(appId);
		// 3.check userId
		ServiceError.assertUserIdForComment(userId);

		// 4.check Object properties
		for (String properName : new String[] { "appId", "sourceTypeId", "sourceId", "content" }) {
			ServiceError.assertPopertiesIsNullForComment(properName);
		}

		// 5. check isMyself
		if (!ObjectUtils.equals(appId, comment.getAppId()) || !ObjectUtils.equals(userId, comment.getUserId())) {
			throw new CommentServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non ower comment!");
		}
		// 6. check assocType
		try {
			CommentType commentTypeDict = commentTypeService.getCommentType(appId, comment.getSourceTypeId());
			if (commentTypeDict == null) {
				throw new CommentServiceException(ServiceError.ERROR_NOT_FOUND, "the CommentType not find by commentTypeId [ " + comment.getSourceTypeId() + "]");
			}
		} catch (CommentTypeServiceException e) {
			e.printStackTrace(System.err);
		}

		// TODO 检查回复人是否存在

		try {
			comment.setCreateAt(System.currentTimeMillis());
			return (Long) this.saveEntity(comment);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new CommentServiceException(e);
		}
	}

	@Override
	public boolean removeComment(Long appId, Long userId, Long commentId) throws CommentServiceException {
		// 1.check appId
		ServiceError.assertAppIdForComment(appId);
		// 2.check userId
		ServiceError.assertUserIdForComment(userId);

		Comment comment = this.getComment(appId, userId, commentId);
		if (comment == null) {
			throw new CommentServiceException(ServiceError.ERROR_NOT_FOUND, "remove " + commentId + " comment not exist");
		}
		// 3. check is myself
		if (!ObjectUtils.equals(comment.getAppId(), appId) || !ObjectUtils.equals(comment.getUserId(), userId)) {
			throw new CommentServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own comment");// 删除的不是自己的收藏夹
		}
		try {
			return deleteEntity(commentId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new CommentServiceException(e);
		}

	}

	@Override
	public Comment getComment(Long appId, Long userId, Long commentId) throws CommentServiceException {
		ServiceError.assertAppIdForComment(appId);
		ServiceError.assertUserIdForComment(userId);
		if (commentId == null) {
			return null;
		}
		try {
			return getEntity(commentId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new CommentServiceException(e);
		}
	}

	@Override
	public int countComment(Long appId, Long sourceTypeId, Long sourceId) throws CommentServiceException {
		try {
			return this.countEntitys(LIST_COMMENT_ID_APPID_SOURCETYPEID_SOURCEID, appId, sourceTypeId, sourceId);
		} catch (BaseServiceException e) {
			throw new CommentServiceException(e.getMessage());
		}
	}

	@Override
	public List<Comment> getComments(Long appId, Long sourceTypeId, Long sourceId, Integer start, Integer count) throws CommentServiceException {
		try {
			return this.getSubEntitys(LIST_COMMENT_ID_APPID_SOURCETYPEID_SOURCEID, start, count, appId, sourceTypeId, sourceId);
		} catch (BaseServiceException e) {
			throw new CommentServiceException(e.getMessage());
		}
	}

	@Override
	public List<Comment> getCommentsByMinId(Long appId, Long sourceTypeId, Long sourceId, Long mindId, Integer count, Boolean forward) throws CommentServiceException {
		int iCount = countComment(appId, sourceTypeId, sourceId);
		if (count == 0 || count < 0) {
			return Collections.emptyList();
		}

		Boolean bForward = forward == null ? Boolean.FALSE : Boolean.TRUE;
		int fullPos = 0;
		Long buffer[] = new Long[count];
		
		List<Long> resultIds = new ArrayList<Long>();
		int iEach = iCount / PAGESIZE;
		iEach = iEach * PAGESIZE == iCount ? iEach : iEach + 1;

		try {
			List<Long> tempIdList = null;
			Boolean bBreak = false;
			for (int i = 0; i < iEach; i++) {
				tempIdList = getSubIds(LIST_COMMENT_ID_APPID_SOURCETYPEID_SOURCEID, i * PAGESIZE, PAGESIZE, appId, sourceTypeId, sourceId);
				if (CollectionUtils.isNotEmpty(tempIdList)) {
					for (Long id : tempIdList) {
						if (id != null) {
							if (!bForward) { //正常取数据
								if (id < mindId) {
									resultIds.add(id);
									if (resultIds.size() == count) {
										bBreak = true;
										break;
									}
								}
							} else {     //反方向取数据
								if (id > mindId) {
									if (fullPos >= count-1 && buffer[fullPos] != null) {
										System.arraycopy(buffer, 1, buffer, 0, buffer.length-1);
									}
									buffer[fullPos] = id;
									if (fullPos < count-1) {
										fullPos++;
									}
								}
							}
						}
					}
				}
				if (bBreak) {
					break;
				}
			}
			if (bForward && buffer[0] != null) {
				for (int i = 0; i < buffer.length; i++) {
					if (buffer[i] != null) {
						resultIds.add(buffer[i]);
					}
				}
			}
			
			if (CollectionUtils.isNotEmpty(resultIds)) {
				return this.getEntityByIds(resultIds);
			}
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	public static void main(String[] args) {
		List<Long> list = new ArrayList<Long>();
		for (long i = 100000; i > 0; i--) {
			list.add(i);
		}

		Long buffer[];
		int count = 10;
		buffer = new Long[count];

		long searchId = 99989;
		List<Long> result = new ArrayList<Long>();
		System.out.println(list);
		int fullPos = 0;
		Boolean bForward = Boolean.TRUE;
		List<Long> tempIdList = null;
		Boolean bBreak = false;
		for (int i = 0; i < list.size(); i++) {
			tempIdList = list.subList(Math.min(i * PAGESIZE, list.size()), Math.min(i * PAGESIZE + PAGESIZE, list.size()));
			if (CollectionUtils.isNotEmpty(tempIdList)) {
				for (Long id : tempIdList) {
					if (id != null) {
						if (!bForward) {
							if (id < searchId) {
								result.add(id);
								if (result.size() == count) {
									bBreak = true;
									break;
								}
							}
						} else {
							if (id > searchId) {
								if (fullPos >= count-1 && buffer[fullPos] != null) {
									System.arraycopy(buffer, 1, buffer, 0, buffer.length-1);
								}
								buffer[fullPos] = id;
								if (fullPos < count-1) {
									fullPos++;
								}
							}
						}
					}
				}
			}
			if (bBreak) {
				break;
			}
		}

		
		
		if (bForward && buffer[0] != null) {
			for (int i = 0; i < buffer.length; i++) {
				if (buffer[i] != null) {
					result.add(buffer[i]);
				}
			}
		}
		
		
		System.out.println(result);
		System.out.print("\n");


	}
}
