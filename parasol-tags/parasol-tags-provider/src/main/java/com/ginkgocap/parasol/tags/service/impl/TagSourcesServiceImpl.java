package com.ginkgocap.parasol.tags.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.tags.exception.TagSourceServiceException;
import com.ginkgocap.parasol.tags.model.TagSource;
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

	// 一个资源下有那些TagSource
	private static final String LIST_ID_APPID_SOURCEID_SOURCETYPE = "List_Id_AppId_SourceId_SourceType";
	private static final String LIST_ID_APPID_TAGID = "List_Id_AppId_TagId";

	private static int MAX_TAG = 20; // 一个资源下最多创建的标签数

	@Override
	public Long createTagSource(TagSource source) throws TagSourceServiceException {
		ServiceError.assertPopertiesIsNullForTagSource("tagId");
		ServiceError.assertPopertiesIsNullForTagSource("appId");
		ServiceError.assertPopertiesIsNullForTagSource("userId");
		ServiceError.assertPopertiesIsNullForTagSource("sourceId");
		ServiceError.assertPopertiesIsNullForTagSource("sourceType");
		int count = countTagSourcesByAppIdSourceIdSourceType(source.getAppId(), source.getSourceId(), source.getSourceType());
		if (count >= MAX_TAG) {
			throw new TagSourceServiceException(ServiceError.ERROR_TOO_MANY, "Can't create too TagSource tag， Max is " + MAX_TAG);
		}

		try {
			return (Long) saveEntity(source);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		}

	}

	@Override
	public boolean removeTagSource(Long appId, Long userId , Long tagSourceId) throws TagSourceServiceException {
		ServiceError.assertAppidIsNullForTagSource(appId);
		ServiceError.assertTagSourceIdIsNullForTagSource(tagSourceId);
		ServiceError.assertUserIdIsNullForTagSource(userId);
		try {
			TagSource tagSource= this.getTagSource(appId, tagSourceId);
			if (tagSource != null) {
				if (ObjectUtils.equals(tagSource.getUserId(), userId)) {
					return this.deleteEntity(tagSourceId);
				} else {
					throw new TagSourceServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own TagSource");
				}
			}
			
			return this.deleteEntity(tagSourceId);
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
			return this.getEntity(tagSourceId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		}
	}
	@Override
	public List<TagSource> getTagSourcesByAppIdSourceIdSourceType(Long appId, Long sourceId, Integer sourceType) throws TagSourceServiceException {
		ServiceError.assertAppidIsNullForTagSource(appId);
		ServiceError.assertTagSourceIdIsNullForTagSource(sourceId);
		ServiceError.assertTagSourceTypeIsNullForTagSource(sourceType);
		try {
			List<TagSource> tagSources = this.getEntitys(LIST_ID_APPID_SOURCEID_SOURCETYPE, appId, sourceId, sourceType);
			return tagSources;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagSourceServiceException(e);
		}
	}

	@Override
	public Integer countTagSourcesByAppIdSourceIdSourceType(Long appId, Long sourceId, Integer sourceType) throws TagSourceServiceException {
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
			return this.getSubEntitys(LIST_ID_APPID_TAGID, iStart, iCount, appId, tagId);
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
				int rem = count % eachNumber; 	 // 余数
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



}
