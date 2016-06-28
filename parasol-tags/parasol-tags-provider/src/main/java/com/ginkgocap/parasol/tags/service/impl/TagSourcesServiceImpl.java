package com.ginkgocap.parasol.tags.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.tags.exception.TagServiceException;
import com.ginkgocap.parasol.tags.exception.TagSourceServiceException;
import com.ginkgocap.parasol.tags.model.Tag;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.service.TagService;
import com.ginkgocap.parasol.tags.service.TagSourceService;

/**
 * 操作TagSource
 * 
 * @author allenshen
 * @date 2015年11月27日
 * @time 上午9:11:50
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("tagSourceService")
public class TagSourcesServiceImpl extends BaseService<TagSource> implements TagSourceService {

	private static Logger logger = Logger.getLogger(TagSourcesServiceImpl.class);
	// 一个资源下有那些TagSource
	private static final String LIST_ID_APPID_SOURCEID_SOURCETYPE = "List_Id_AppId_SourceId_SourceType";
	private static final String LIST_ID_APPID_TAGID = "List_Id_AppId_TagId";
	private static final String LIST_ID_APPID_TAGID_SOURCETYPE = "List_Id_AppId_TagId_SourceType";
	private static final String LIST_BY_APPID_TAGID_SOURCETYPE = "List_By_AppId_TagId_SourceType";
	private static final String DELETE_BY_APPID_SOURCEID_SOURCETYPE = "Delete_By_AppId_SourceId_SourceType";

	private static int MAX_TAG = 10; // 一个资源下最多创建的标签数

	@Autowired
	private TagService tagService;

	@Override
	public Long createTagSource(TagSource source) throws TagSourceServiceException {
		ServiceError.assertPopertiesIsNullForTagSource("tagId");
		ServiceError.assertPopertiesIsNullForTagSource("appId");
		ServiceError.assertPopertiesIsNullForTagSource("userId");
		ServiceError.assertPopertiesIsNullForTagSource("sourceId");
		ServiceError.assertPopertiesIsNullForTagSource("sourceType");

		// 检查Tag是否存在,系统标签也可以处理
		try {
			Tag tag = tagService.getTag(source.getUserId(), source.getTagId());
			// TODO : 添加系统标签的处理
			if (tag == null) {
				throw new TagSourceServiceException(ServiceError.ERROR_NOT_FOUND, "The label [" + source.getTagId() + "] you want to add does not exist"); // 要添加的标签不存在！
			}
		} catch (TagServiceException tagException) {
			tagException.printStackTrace(System.err);
			throw new TagSourceServiceException(tagException);
		}

		int count = countTagSourcesByAppIdSourceIdSourceType(source.getAppId(), source.getSourceId(), source.getSourceType());
		if (count >= MAX_TAG) {
			throw new TagSourceServiceException(ServiceError.ERROR_TOO_MANY, "Can't create too TagSource tag， Max is " + MAX_TAG);
		}

		try {
			if (count > 0) {
				List<TagSource> tagSources = getTagSourcesByAppIdSourceIdSourceType(source.getAppId(), source.getSourceId(), source.getSourceType());
				for (TagSource existTagSource : tagSources) {
					if (existTagSource != null && ObjectUtils.equals(existTagSource.getTagId(), source.getTagId())) {
						TagSourceServiceException te = new TagSourceServiceException(ServiceError.ERROR_OBJECT_EXIST, "The  " + source.getTagId()
								+ " tag that you want to create already exists");
						logger.error(te.getErrorCode() + " " + te.getMessage());
						throw te;
					}
				}
			}
			return (Long) saveEntity(source);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		}

	}

	@Override
	public boolean removeTagSource(Long appId, Long userId, Long tagSourceId) throws TagSourceServiceException {
		ServiceError.assertAppidIsNullForTagSource(appId);
		ServiceError.assertTagSourceIdIsNullForTagSource(tagSourceId);
		ServiceError.assertUserIdIsNullForTagSource(userId);
		try {
			TagSource tagSource = this.getTagSource(appId, tagSourceId);
			if (tagSource != null) {
				if (ObjectUtils.equals(tagSource.getUserId(), userId) && ObjectUtils.equals(tagSource.getAppId(), appId)) {
					return this.deleteEntity(tagSourceId);
				} else {
					throw new TagSourceServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own TagSource");
				}
			} else {
				throw new TagSourceServiceException(ServiceError.ERROR_NOT_FOUND, "TagSource is not exist");
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		}
	}
	
	@Override
	public int removeTagSourceBySourceId(Long appId, Long userId, Long sourceId,Long sourceType) throws TagSourceServiceException
	{
		ServiceError.assertAppidIsNullForTagSource(appId);
		ServiceError.assertTagSourceIdIsNullForTagSource(sourceId);
		ServiceError.assertUserIdIsNullForTagSource(userId);
		ServiceError.assertUserIdIsNullForTagSource(sourceType);
		try {
			return this.deleteList(DELETE_BY_APPID_SOURCEID_SOURCETYPE, appId, userId, sourceId, sourceType);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		}
	}

	@Override
	public TagSource getTagSource(Long appId, Long tagSourceId) throws TagSourceServiceException {
		ServiceError.assertAppidIsNullForTagSource(appId);
		ServiceError.assertTagSourceIdIsNullForTagSource(tagSourceId);
		try {
			TagSource ts = this.getEntity(tagSourceId);
			if (ts != null) {
				Tag tag = tagService.getTag(ts.getUserId(), ts.getTagId());
				if (tag != null) {
					ts.setTagName(tag.getTagName());
				} else {
					logger.warn("don't find the tag by id : " + ts.getTagId());
					return null; // Tag不存在的时候，直接返回标示TagSource也不存在了
				}
			}
			return ts;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		} catch (TagServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		}
	}

	@Override
	public List<TagSource> getTagSourcesByAppIdSourceIdSourceType(Long appId, Long sourceId, Long sourceType) throws TagSourceServiceException {
		ServiceError.assertAppidIsNullForTagSource(appId);
		ServiceError.assertTagSourceIdIsNullForTagSource(sourceId);
		ServiceError.assertTagSourceTypeIsNullForTagSource(sourceType);
		try {
			List<TagSource> tagSources = this.getEntitys(LIST_ID_APPID_SOURCEID_SOURCETYPE, appId, sourceId, sourceType);
			List<TagSource> result = new ArrayList<TagSource>();

			if (CollectionUtils.isNotEmpty(tagSources)) {
				// 按用户分组
				Map<Long, List<Long>> userTagIdsMap = new HashMap<Long, List<Long>>();
				for (TagSource tagSource : tagSources) {
					if (tagSource != null) {
						Long userId = tagSource.getUserId();
						List<Long> tagIds = userTagIdsMap.get(userId);
						if (tagIds == null) {
							tagIds = new ArrayList<Long>();
							userTagIdsMap.put(userId, tagIds);
						}
						tagIds.add(tagSource.getTagId());
					}
				}

				Map<Long, String> tagMap = new HashMap<Long, String>();
				// 开始查询每个用户的标签
				if (MapUtils.isNotEmpty(userTagIdsMap)) {
					for (Long userId : userTagIdsMap.keySet()) {
						List<Long> tagIds = userTagIdsMap.get(userId);
						if (CollectionUtils.isNotEmpty(tagIds)) {
							List<Tag> tags = tagService.getTags(userId, tagIds);
							for (Tag tag : tags) {
								if (tag != null) {
									tagMap.put(tag.getId(), tag.getTagName());
								}
							}
						}
					}
				}

				// 开始组装
				if (MapUtils.isNotEmpty(tagMap)) {
					for (TagSource tagSource : tagSources) {
						if (tagSource != null) {
							String tagName = tagMap.get(tagSource.getTagId());
							if (StringUtils.isNotBlank(tagName)) {
								tagSource.setTagName(tagName);
								result.add(tagSource);
							}
						}
					}
				}

			}

			return result;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		} catch (TagServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		}
	}

	@Override
	public Integer countTagSourcesByAppIdSourceIdSourceType(Long appId, Long sourceId, Long sourceType) throws TagSourceServiceException {
		ServiceError.assertAppidIsNullForTagSource(appId);
		ServiceError.assertTagSourceIdIsNullForTagSource(sourceId);
		ServiceError.assertTagSourceTypeIsNullForTagSource(sourceType);
		try {
			return this.countEntitys(LIST_ID_APPID_SOURCEID_SOURCETYPE, appId, sourceId, sourceType);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		}
	}

	@Override
	public List<TagSource> getTagSourcesByAppIdTagId(Long appId, Long tagId, Integer iStart, Integer iCount) throws TagSourceServiceException {
		ServiceError.assertTagIdIsNullForTagSource(tagId);
		ServiceError.assertAppidIsNullForTagSource(appId);
		try {
			return this.getSubEntitys(LIST_ID_APPID_TAGID_SOURCETYPE, iStart, iCount, appId, tagId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		}
	}

	@Override
	public List<TagSource> getTagSourcesByAppIdTagIdAndType(Long appId, Long tagId, Long sourceType, Integer iStart, Integer iCount) throws TagSourceServiceException {
		ServiceError.assertTagIdIsNullForTagSource(tagId);
		ServiceError.assertAppidIsNullForTagSource(appId);
		try {
			return this.getSubEntitys(LIST_BY_APPID_TAGID_SOURCETYPE, iStart, iCount, new Object[]{appId, tagId, sourceType});
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		}
	}

	@Override
	public Integer countTagSourcesByAppIdTagId(Long appId, Long tagId) throws TagSourceServiceException {
		ServiceError.assertTagIdIsNullForTagSource(tagId);
		ServiceError.assertAppidIsNullForTagSource(appId);
		try {
			return countEntitys(LIST_ID_APPID_TAGID, appId, tagId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		}
	}

	@Override
	public boolean removeTagSourcesByTagId(Long appId, Long tagId) throws TagSourceServiceException {
		ServiceError.assertTagIdIsNullForTagSource(tagId);
		ServiceError.assertAppidIsNullForTagSource(appId);
		try {
			Integer count = countTagSourcesByAppIdTagId(appId, tagId);
			if (count != null && count > 0) {
				int eachNumber = 20;
				int cycles = count / eachNumber; // 每次删除20个
				int rem = count % eachNumber; // 余数
				cycles = rem > 0 ? cycles + 1 : cycles;
				for (int i = 0; i < cycles; i++) {
					int start = i * eachNumber;
					List<Long> sourceIds = this.getSubIds(LIST_ID_APPID_TAGID, start, eachNumber, appId, tagId);
					if (CollectionUtils.isNotEmpty(sourceIds)) {
						this.deleteEntityByIds(sourceIds);
					}
				}
			}
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public boolean createTagSources(List<TagSource> tagSource)
			throws TagSourceServiceException {
		// TODO Auto-generated method stub
		return false;
	}

}
