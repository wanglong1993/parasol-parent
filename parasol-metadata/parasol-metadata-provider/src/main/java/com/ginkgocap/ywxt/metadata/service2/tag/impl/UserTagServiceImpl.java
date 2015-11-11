package com.ginkgocap.ywxt.metadata.service2.tag.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.metadata.model.UserTag;
import com.ginkgocap.ywxt.metadata.service.userTag.UserTagService;
import com.ginkgocap.ywxt.metadata.service2.BaseService;
import com.ginkgocap.ywxt.metadata.service2.exception.BaseServiceException;

/**
 * 
 * @author allenshen
 * @date: 2015年10月21日
 * @time: 上午9:48:56
 * Copyright©2015 www.gintong.com
 */
@Service("userTagService")
public class UserTagServiceImpl extends BaseService<UserTag> implements UserTagService {
	private static final String USERTAG_LIST_ID_USERID_TAGNAME = "UserTag_List_Id_UserId_TagName";
	private static final String USERTAG_LIST_ID_USERID = "UserTag_List_Id_UserId";
	private static final Long SYS_USER_ID = 0l;

	@Override
	public UserTag selectById(long id) {
		try {
			return getEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserTag insert(UserTag userTag) {
		try {
			Long id = (Long) saveEntity(userTag);
			if (id != null) {
				userTag.setTagId(id);
				return userTag;
			} else {
				return null;
			}

		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean insert(List<UserTag> userTagList) {
		try {
			List<Serializable> ids = saveEntitys(userTagList);
			if (CollectionUtils.isNotEmpty(userTagList)) {
				return ids.size() == userTagList.size() ? true : false;
			} else {
				return true;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(UserTag UserTag) {
		try {
			updateEntity(UserTag);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void delete(long id) {
		try {
			deleteEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteByTagNameAndUserId(long userId, String tagName) {
		try {
			this.deleteList(USERTAG_LIST_ID_USERID_TAGNAME, userId, tagName);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}
	
	
	
	@Deprecated
	@Override
	public List<UserTag> selectByNameAndUserId(long userId, String tagName) {
		try {
			return this.getEntitys(USERTAG_LIST_ID_USERID_TAGNAME, userId, tagName);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserTag> selectPageByUserId(long userId, long startRow, int pageSize) {
		try {
			return this.getSubEntitys(USERTAG_LIST_ID_USERID, (int)startRow, pageSize, userId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public long countByUserId(long userId) {
		try {
			return this.countEntitys(USERTAG_LIST_ID_USERID, userId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	/**
	 * It is recommended to use selectPageByUserId
	 */
	@Override
	@Deprecated
	public List<UserTag> selectPageByUserIdAndSys(long userId, long startRow, int pageSize) {
		List<UserTag> sysTags = null;
		sysTags = selectPageByUserId(SYS_USER_ID, startRow, pageSize);
		return sysTags;
	}

	@Override
	public long countBySys() {
		return countByUserId(SYS_USER_ID);
	}

	@Override
	public List<UserTag> selectPageBySys(long startRow, int pageSize) {
		return selectPageByUserId(SYS_USER_ID, startRow, pageSize);
	}

	/**
	 * It is recommended to use countByUserId
	 */
	@Override
	@Deprecated
	public long countByUserIdAndSys(long userId) {
		return countByUserId(SYS_USER_ID);
	}

	
	@Deprecated
	@Override
	public List<UserTag> selectTagNameInUserIdAndSys(long userId, String tagName) {
		return selectByNameAndUserId(SYS_USER_ID, tagName);
	}

	
	/**
	 * 还的包括系统的Tag
	 */
	@Override
	public List<UserTag> searchPrefixPageByUserIdAndSys(long userId, String[] keywords, long startRow, int pageSize) {
		List<UserTag> userTags = searchPrefixPageByUserId(userId, keywords, startRow, pageSize);
		userTags = userTags == null ? new ArrayList<UserTag>(): userTags;
		if (userTags.size() < pageSize) {
			//找系统的Tag
			List<UserTag> sysUserTags = searchPrefixPageByUserId(SYS_USER_ID, keywords, userTags.size(), pageSize-userTags.size());
			if (CollectionUtils.isNotEmpty(sysUserTags)) {
				userTags.addAll(sysUserTags);
			}
		}
		return userTags.subList(0, Math.min(pageSize, userTags.size()));
	}

	@Deprecated
	@Override
	public List<UserTag> searchContainPageByUserIdAndSys(long userId, String[] keywords, long startRow, int pageSize) {
		try {
			List<UserTag> lResult = new ArrayList<UserTag>();
			int take_number = 0;

			while (true) {
				List<UserTag> userTags = getSubEntitys(USERTAG_LIST_ID_USERID, take_number * TAKE_SIZE, TAKE_SIZE, userId);
				if (CollectionUtils.isNotEmpty(userTags)) {
					if (ArrayUtils.isNotEmpty(keywords)) {
						for (UserTag userTag : userTags) {
							if (userTag != null) {
								for (String keyWord : keywords) {
									if (StringUtils.isNotBlank(keyWord) && StringUtils.contains(userTag.getTagName(), keyWord)) {
										lResult.add(userTag);
									}
								}
							}
						}
					} else {
						lResult.addAll(userTags);
					}
				}

				// 查不到数据数据时退出，或者查的次数超过100次太多
				if (CollectionUtils.isEmpty(userTags) || userTags.size() < TAKE_SIZE || take_number > 100) {
					break;
				}
				take_number++;
			}
			return lResult.subList(Math.min((int) startRow, lResult.size()), Math.min((int) startRow + pageSize, lResult.size()));
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Class<UserTag> getEntityClass() {
		// TODO Auto-generated method stub
		return UserTag.class;
	}

	
	private List<UserTag> searchPrefixPageByUserId(long userId, String[] keywords, long startRow, int pageSize) {
		try {
			List<UserTag> lResult = new ArrayList<UserTag>();
			int take_number = 0;
			int skip_number = 0;
			while (true) {
				List<UserTag> userTags = getSubEntitys(USERTAG_LIST_ID_USERID, take_number * TAKE_SIZE, TAKE_SIZE, userId);
				if (CollectionUtils.isNotEmpty(userTags)) {
					if (ArrayUtils.isNotEmpty(keywords)) {
						for (UserTag userTag : userTags) {
							if (userTag != null) {
								for (String keyWord : keywords) {
									if (StringUtils.isNotBlank(keyWord) && StringUtils.startsWith(userTag.getTagName(), keyWord)) {
										if (skip_number >= startRow) {
											lResult.add(userTag);
										}
										skip_number++;
									}
								}
							}
						}
					} else {
						lResult.addAll(userTags);
					}
				}

				// 查不到数据数据时退出，或者查的次数超过100次太多
				if (CollectionUtils.isEmpty(userTags) || userTags.size() < TAKE_SIZE || skip_number >= startRow + pageSize ||take_number > 100) {
					break;
				}
				take_number++;
			}
			return lResult.subList(0, Math.min(pageSize,lResult.size()));
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}
}
