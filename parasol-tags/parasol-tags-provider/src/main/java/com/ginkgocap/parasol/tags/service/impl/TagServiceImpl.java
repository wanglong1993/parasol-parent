package com.ginkgocap.parasol.tags.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ginkgocap.ywxt.util.PinyinUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.tags.exception.TagServiceException;
import com.ginkgocap.parasol.tags.exception.TagSourceServiceException;
import com.ginkgocap.parasol.tags.model.Tag;
import com.ginkgocap.parasol.tags.service.TagService;
import com.ginkgocap.parasol.tags.service.TagSourceService;

/**
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午4:26:56
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("tagService")
public class TagServiceImpl extends BaseService<Tag> implements TagService {
	private static Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);
	private static final String LIST_TAG_ID_USERID_APPID_TAGTYPE = "List_Tag_Id_UserId_AppId_TagType";
	private static final String LIST_TAG_ID_USERID_APPID_TAGTYPE_DEFAULT = "List_Tag_Id_UserId_AppId_TagType_Default";
	private static final String LIST_TAG_ID_USERID_IDS = "List_Tag_Id_UserId_Ids";

	private static final int MAX_TAG = 300; // 最多创建的标签数量
	private static final int MAX_LEN_TAG = 30; // Tag的长度30个字符

	@Autowired
	private TagSourceService tagSourceService;

	@Override
	public Long createTag(Long userId, Tag tag) throws TagServiceException {
		ServiceError.assertUserIdIsNull(userId); // 检查用户ID
		ServiceError.assertInputTagIsNull(tag); // 检查Tag对象
		ServiceError.assertPopertiesIsNullForTag("userId");
		ServiceError.assertPopertiesIsNullForTag("appId");
		ServiceError.assertPopertiesIsNullForTag("tagName");
		tag.setUserId(userId);
		tag.setFirstIndex(PinyinUtils.stringToHeads(tag.getTagName()));
		if (!ObjectUtils.equals(userId, tag.getUserId())) {
			throw new TagServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own tag!");
		}

		// Tag 不能是空的
		if (StringUtils.isBlank(tag.getTagName())) {
			throw new TagServiceException(ServiceError.ERROR_TAG_NAME_IS_BLANK, "tagName must have value ");
		}

		// 检查长度
		/*if (StringUtils.length(tag.getTagName()) > MAX_LEN_TAG) {
			throw new TagServiceException(ServiceError.ERROR_TAG_NAME_TOO_LENGTH, "tagName too length， max is " + MAX_LEN_TAG);
		}*/

		//tag.setTagName(tag.getTagName().replace(" ", "")); //replace tab blank , 有英文情况
		int count = countTagsByUserIdAppidTagType(userId, tag.getAppId(), tag.getTagType());
		if (count >= MAX_TAG) {
			throw new TagServiceException(ServiceError.ERROR_TOO_MANY, "Can't create too many tag， Max is " + MAX_TAG);
		}
		@SuppressWarnings("unchecked")
		List<Tag> tags = count > 0 ? getTagsByUserIdAppidTagType(userId, tag.getAppId(), tag.getTagType()) : ListUtils.EMPTY_LIST;

		if (!CollectionUtils.isEmpty(tags)) {
			for (Tag existTag : tags) {
				if (ObjectUtils.equals(tag.getTagName(), existTag.getTagName())) {
					throw new TagServiceException(ServiceError.ERROR_OBJECT_EXIST, "The  " + tag.getTagName() + " tag that you want to create already exists");
				}
			}
		}

		try {
			return (Long) saveEntity(tag);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagServiceException(e);
		}
	}

	@Override
	public boolean removeTag(Long userId, long tagId) throws TagServiceException {
		ServiceError.assertUserIdIsNull(userId); // 检查用户ID
		ServiceError.assertTagIdIsNull(userId); // 检查tagId
		Tag tag = null;
		try {
			tag = this.getTag(userId, tagId);
			if (tag != null) {
				// 删除所有使用指定Tag的TagSource
				// TODO 可以修改成异步删除
				tagSourceService.removeTagSourcesByTagId(tag.getAppId(), tag.getId());
			} else {
				throw new TagServiceException(ServiceError.ERROR_NOT_FOUND, "delete tag don't exist!");
			}
		} catch (TagSourceServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			logger.error("delete from tagSources fail: by [appId:" + tag.getAppId() + ",tagId:" + tag.getId() + "]");
		}

		try {
			this.deleteEntity(tagId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagServiceException(e);
		}

		return true;
	}

	@Override
	public boolean updateTag(Long userId, Tag tag) throws TagServiceException {
		ServiceError.assertPopertiesIsNullForTag("id");
		ServiceError.assertPopertiesIsNullForTag("tagName");
		ServiceError.assertUserIdIsNull(userId);

		Tag oldTag = this.getTag(userId, tag.getId());

		if (oldTag == null) {
			throw new TagServiceException(ServiceError.ERROR_NOT_FOUND, "update tag don't exist!");
		}

		if (!ObjectUtils.equals(userId, tag.getUserId())) {
			throw new TagServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own tag!");

		}
		oldTag.setTagName(tag.getTagName());
		//TagType must same with old
		tag.setTagType(oldTag.getTagType());

		try {
			this.updateEntity(tag);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagServiceException(e);
		}
		return true;
	}

	@Override
	public Tag getTag(Long userId, Long id) throws TagServiceException {
		ServiceError.assertTagIdIsNull(id);
		// ServiceError.assertUserIdIsNull(userId);
		try {
			Tag tag = this.getEntity(id);
			if (tag != null && ObjectUtils.equals(tag.getUserId(), userId)) {
				return tag;
			} else {
				if (tag != null) {
					logger.warn("tag userId property : " + tag.getUserId() + " not equal " + userId);
				}
				return null;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagServiceException(e);
		}
	}

	@Override
	public Tag getTag(Long id) throws TagServiceException {
		ServiceError.assertTagIdIsNull(id);
		// ServiceError.assertUserIdIsNull(userId);
		try {
			Tag tag = this.getEntity(id);
			if (tag != null) {
				return tag;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagServiceException(e);
		}
		return null;
	}

	@Override
	public List<Tag> getTags(Long userId, List<Long> ids) throws TagServiceException {
		List<Tag> result = new ArrayList<Tag>();
		if (userId != null && CollectionUtils.isNotEmpty(ids)) {
			try {
				List<Tag> tags = this.getEntityByIds(ids);
				if (CollectionUtils.isNotEmpty(tags)) {
					for (Tag tag : tags) {
						//userId = -1,For the case: if get others public resource that contain these tags;
						if (userId.intValue() == -1 || (tag != null && ObjectUtils.equals(tag.getUserId(), userId))) {
							result.add(tag);
						}
					}
				}
			} catch (BaseServiceException e) {
				e.printStackTrace(System.err);
				throw new TagServiceException(e);
			}
		}
		return result;
	}

	@Override
	public List<Tag> getTagsByUserIdAppidTagType(Long userId, Long appId, Long tagType) throws TagServiceException {
		ServiceError.assertUserIdIsNull(userId); // 检查用户ID
		try {
			List<Long> ids = this.getIds(LIST_TAG_ID_USERID_APPID_TAGTYPE, userId, appId, tagType);
			if (CollectionUtils.isNotEmpty(ids)) {
				return this.getEntityByIds(ids);
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagServiceException(e);
		}
		return null;
	}
	
	@Override
	public List<Tag> getTagsByUserIdAppidTagTypePage(Long userId, Long appId, Long tagType, int start, int size) throws TagServiceException {
		//ServiceError.assertUserIdIsNull(userId); // 检查用户ID
		try {
			List<Tag> tagList = this.getSubEntitys(LIST_TAG_ID_USERID_APPID_TAGTYPE_DEFAULT,start,size, userId, appId, tagType);

			if(CollectionUtils.isNotEmpty(tagList)){
				return tagList;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagServiceException(e);
		}
		return new ArrayList<Tag>();
	}

	@Override
	public boolean batchDeleteTags(long userId, List<Long> ids) {

		boolean flag = false;
		try {
			flag = this.deleteEntityByIds(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public int countTagsByUserIdAppidTagType(Long userId, Long appId, Long tagType) throws TagServiceException {
		ServiceError.assertUserIdIsNull(userId); // 检查用户ID
		try {
			return this.countEntitys(LIST_TAG_ID_USERID_APPID_TAGTYPE, userId, appId, tagType);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagServiceException(e);
		}
	}
	@Override
	public int countDefaultTagsByUserIdAppidTagType(Long userId, Long appId, Long tagType) throws TagServiceException {
		//ServiceError.assertUserIdIsNull(userId); // 不用检查用户ID
		try {
			return this.countEntitys(LIST_TAG_ID_USERID_APPID_TAGTYPE_DEFAULT, userId, appId, tagType);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new TagServiceException(e);
		}
	}

}
