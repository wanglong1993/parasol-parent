package com.ginkgocap.parasol.favorite.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.favorite.exception.FavoriteServiceException;
import com.ginkgocap.parasol.favorite.model.Favorite;
import com.ginkgocap.parasol.favorite.service.FavoriteService;
import com.ginkgocap.parasol.favorite.service.FavoriteTypeService;

/**
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午4:26:56
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("favoriteService")
public class FavoriteServiceImpl extends BaseService<Favorite> implements FavoriteService {
	private static Logger logger = Logger.getLogger(FavoriteServiceImpl.class);
	private static int len_name = 50;

	private static String LIST_DIRECTORY_PID_ID = "List_Favorite_Id_Pid"; // 查询子节点
	private static String LIST_DIRECTORY_ID_APPID_USERID_TYPEID_PID = "List_Favorite_Id_AppId_UserId_TypeId_Pid"; // 查询一个应用的分类根收藏夹

	@Autowired
	private FavoriteTypeService favoriteTypeService;

	/**
	 * 创建应用分类下边的根收藏夹 比如创建系统应用知识下的根收藏夹
	 */
	@Override
	public Long createFavoriteForRoot(Long favoriteTypeId, Favorite favorite) throws FavoriteServiceException {
		return createFavoriteForChildren(0l, favorite);
	}

	@Override
	public Long createFavoriteForChildren(Long pId, Favorite favorite) throws FavoriteServiceException {
		// 1.check input parameters(appId, typeId, name，userId)
		ServiceError.assertFavoriteForFavorite(favorite);
		// 1.1 checkAppid
		if (ObjectUtils.equals(favorite.getAppId(), null) || favorite.getAppId() <= 0) { // appId
			throw new FavoriteServiceException(ServiceError.ERROR_PERTIES, "appId property must have a value and greater than zero");
		}
//		// @formatter:off
//		// 1.2 check type id
//		if (favorite.getTypeId() <= 0) { // type 必须有值且的大于1
//			throw new FavoriteServiceException(ServiceError.ERROR_PERTIES, "type property must have a value and greater than zero");
//		} 

//		else {
//			// 1.2.1 检查收藏夹树分类是否存在
//			FavoriteType favoriteType = null;
//			try {
//				favoriteType = favoriteTypeService.getFavoriteType(favorite.getAppId(), favorite.getTypeId());
//			} catch (FavoriteTypeServiceException e) {
//				if (logger.isDebugEnabled()) {
//					e.printStackTrace(System.err);
//				}
//				throw new FavoriteServiceException(e);
//			}
//			if (favoriteType == null) {
//				StringBuffer sb = new StringBuffer();
//				sb.append("don't find the FavoriteType by appid is ").append(favorite.getAppId()).append(" and type is ").append(favorite.getTypeId());
//				throw new FavoriteServiceException(ServiceError.ERROR_NOT_FOUND, sb.toString());
//			}
//		}
//		// @formatter:on
		// 1.3 检查名字
		// TODO: 检查敏感词
		logger.info("请检查敏感词");
		favorite.setName(StringUtils.trim(favorite.getName()));
		if (StringUtils.isBlank(favorite.getName())) { // 名字不能为空
			throw new FavoriteServiceException(ServiceError.ERROR_PERTIES, "name property must have a non blank string");
		} else {
			if (favorite.getName().length() > len_name) {
				throw new FavoriteServiceException(ServiceError.ERROR_NAME_LIMIT, favorite.getName() + "  length can not be more than " + len_name);
			}
		}

		// 1.4 用户Id
		if (favorite.getUserId() <= 0) {
			throw new FavoriteServiceException(ServiceError.ERROR_USER_EXIST, "pelase set userId property");
		} else {
			// TODO: 检查用户是否存在
			logger.info("Please implement check user exist condition");
		}

		// 2. 检查父节点和现在的节点情况
		Favorite parentFavorite = null;
		if (pId != null && pId != 0) {
			parentFavorite = this.getFavorite(favorite.getAppId(), favorite.getUserId(), pId);
			if (parentFavorite == null) {
				StringBuffer sb = new StringBuffer();
				sb.append("don't find the parent Favorite by appid is ").append(favorite.getAppId()).append(" userId is ").append(favorite.getUserId());
				sb.append(" or favorite type mismatched parent favorite type");
				throw new FavoriteServiceException(ServiceError.ERROR_NOT_FOUND, sb.toString());
			} else {
				favorite.setPid(pId);
				favorite.setTypeId(parentFavorite.getTypeId());
			}
		}

		// 3. 检查有没有重名根收藏夹
		List<Favorite> favorites = null;
		if (pId != null && pId != 0) { // 创建非根节点
			favorites = getFavoritesByParentId(favorite.getAppId(), favorite.getUserId(), pId);
		} else { // 创建根节点
			favorites = getFavoritesForRoot(favorite.getAppId(), favorite.getUserId(), favorite.getTypeId());
		}
		assertDuplicateName(favorites, favorite);

		// 4.创建
		try {
			favorite.setPid(pId);
			favorite.setUpdateAt(System.currentTimeMillis());
			Long id = (Long) this.saveEntity(favorite);
			if (id > 0) {
				favorite.setId(id);
				String parentNumberCode = getParentNumberCode(parentFavorite);
				favorite.setNumberCode(parentNumberCode + "-" + id);
				this.updateEntity(favorite);
			}
			return id;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteServiceException(e);
		}
	}

	@Override
	public boolean removeFavorite(Long appId, Long userId, Long favoriteId) throws FavoriteServiceException {
		// 1.check input parameters
		ServiceError.assertAppIdForFavorite(appId);
		ServiceError.assertUserIdForFavorite(userId);
		ServiceError.assertFavoriteIdForFavorite(favoriteId);
		try {
			Favorite favorite = this.getEntity(favoriteId);

			if (favorite != null) {
				if (!ObjectUtils.equals(favorite.getAppId(), appId) || !ObjectUtils.equals(favorite.getUserId(), userId)) {
					throw new FavoriteServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own favorite");// 删除的不是自己的收藏夹
				}
			} else {
				throw new FavoriteServiceException(ServiceError.ERROR_NOT_FOUND, "remove " + favoriteId + " favorite not exist");
			}

			// 删除Sub Dir
			List<Favorite> subFavorites = this.getFavoritesByParentId(appId, userId, favoriteId);
			if (!CollectionUtils.isEmpty(subFavorites)) {
				for (Favorite subFavorite : subFavorites) {
					this.removeFavorite(appId, userId, subFavorite.getId());
				}
			}

			// 删除自己
			return this.deleteEntity(favoriteId);

		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteServiceException(e);
		}

	}

	@Override
	public boolean updateFavorite(Long appId, Long userId, Favorite favorite) throws FavoriteServiceException {
		// 1.check input parameters
		ServiceError.assertAppIdForFavorite(appId);
		ServiceError.assertUserIdForFavorite(userId);
		// find old favorite
		try {
			if (favorite != null) {
				ServiceError.assertFavoriteIdForFavorite(favorite.getId());
				Favorite oldFavorite = getEntity(favorite.getId());
				if (oldFavorite != null) {
					if (!ObjectUtils.equals(oldFavorite.getAppId(), appId) || !ObjectUtils.equals(oldFavorite.getUserId(), userId)) {
						throw new FavoriteServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own favorite");// 更新的不是自己的收藏夹
					}

					// 检查名字
					// TODO: 检查敏感词
					logger.info("请检查敏感词");
					favorite.setName(StringUtils.trim(favorite.getName()));
					if (StringUtils.isBlank(favorite.getName())) { // 名字不能为空
						throw new FavoriteServiceException(ServiceError.ERROR_PERTIES, "name property must have a non blank string");
					} else {
						if (favorite.getName().length() > len_name) {
							throw new FavoriteServiceException(ServiceError.ERROR_NAME_LIMIT, favorite.getName() + "  length can not be more than " + len_name);
						}
					}

					// 检查重名
					List<Favorite> favorites = oldFavorite.getPid() != 0 ? this
							.getFavoritesByParentId(oldFavorite.getAppId(), oldFavorite.getUserId(), oldFavorite.getPid())
							: getFavoritesForRoot(oldFavorite.getAppId(), oldFavorite.getUserId(), oldFavorite.getTypeId());
					assertDuplicateName(favorites, favorite);

					// 确保如下数据不能修改
					favorite.setAppId(oldFavorite.getAppId());
					favorite.setUserId(oldFavorite.getUserId());
					favorite.setTypeId(oldFavorite.getTypeId());
					favorite.setPid(oldFavorite.getPid());
					favorite.setNumberCode(oldFavorite.getNumberCode());

					favorite.setUpdateAt(System.currentTimeMillis());
					return this.updateEntity(favorite);
				} else {
					logger.info("don't find the old Favorite entity by id " + favorite.getId());
				}
			} else {
				logger.info("update diectory entity is null");
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteServiceException(e);
		}
		return false;
	}

	@Override
	public boolean moveFavoriteToFavorite(Long appId, Long userId, Long favoriteId, Long toFavoriteId) throws FavoriteServiceException {
		// check parameter
		ServiceError.assertAppIdForFavorite(appId);
		ServiceError.assertUserIdForFavorite(userId);
		ServiceError.assertFavoriteIdForFavorite(favoriteId);
		ServiceError.assertFavoriteIdForFavorite(toFavoriteId);

		try {
			Favorite targetFavorite = this.getEntity(favoriteId);
			if (targetFavorite == null) {
				StringBuffer sb = new StringBuffer();
				sb.append("don't find the from Favorite by id " + favoriteId);
				throw new FavoriteServiceException(ServiceError.ERROR_NOT_FOUND, sb.toString());
			}

			Favorite to = this.getEntity(toFavoriteId);
			if (to == null) {
				StringBuffer sb = new StringBuffer();
				sb.append("don't find the to Favorite by id " + toFavoriteId);
				throw new FavoriteServiceException(ServiceError.ERROR_NOT_FOUND, sb.toString());
			}

			// 检查是不是同一个人，同一个应用，同一个分类
			if (ObjectUtils.equals(targetFavorite.getUserId(), to.getUserId()) && ObjectUtils.equals(targetFavorite.getAppId(), to.getAppId()) && ObjectUtils.equals(targetFavorite.getTypeId(), to.getTypeId())) {
				targetFavorite.setPid(toFavoriteId);
				String numbreCode = getParentNumberCode(to);
				targetFavorite.setNumberCode(numbreCode+"-" + numbreCode); //更新索引
				return this.updateEntity(targetFavorite);
			} else {
				throw new FavoriteServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own favorite");// 移动的不是自己的收藏夹
			}
			
			// TODO 还的检查不能移动到自己的子收藏夹下
			// TODO 不能存在相同的文件名字
			// TODO 检查是不是同一个分类

		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteServiceException(e);
		}

	}

	@Override
	public Favorite getFavorite(Long appId, Long userId, Long id) throws FavoriteServiceException {
		ServiceError.assertAppIdForFavorite(appId);
		ServiceError.assertUserIdForFavorite(userId);
		if (id == null) {
			return null;
		}
		Favorite favorite;
		try {
			favorite = getEntity(id);
			if (favorite != null) {
				if (ObjectUtils.equals(favorite.getAppId(), appId) && ObjectUtils.equals(favorite.getUserId(), userId)) {
					return favorite;
				} else {
					throw new FavoriteServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own favorite");// 查询的不是指定应用和指定用户的Favorite
				}
			} else {
				return null;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteServiceException(e);
		}
	}

	/**
	 * 最多返回500
	 * 
	 * @param appId
	 * @param userId
	 * @param pId
	 * @return
	 * @throws FavoriteServiceException
	 */
	@Override
	public List<Favorite> getFavoritesByParentId(Long appId, Long userId, Long pId) throws FavoriteServiceException {
		// check parameters
		ServiceError.assertAppIdForFavorite(appId);
		ServiceError.assertUserIdForFavorite(userId);
		ServiceError.assertFavoriteIdForFavorite(pId);

		Favorite parentFavorite = null;
		try {
			parentFavorite = this.getEntity(pId);
			if (parentFavorite != null) { // 检查父对象是否存在
				if (ObjectUtils.equals(parentFavorite.getAppId(), appId) && ObjectUtils.equals(parentFavorite.getUserId(), userId)) {
					return getSubEntitys(LIST_DIRECTORY_PID_ID, 0, 500, pId);
				} else {
					throw new FavoriteServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own favorite");// 移动的不是自己的收藏夹
				}
			} else {
				throw new FavoriteServiceException(ServiceError.ERROR_NOT_FOUND, "don't find the parent favorite by id " + pId);
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteServiceException(e);
		}
	}

	@Override
	public int countFavoritesByParentId(Long appId, Long userId, Long pId) throws FavoriteServiceException {
		// check parameters
		ServiceError.assertAppIdForFavorite(appId);
		ServiceError.assertUserIdForFavorite(userId);
		ServiceError.assertFavoriteIdForFavorite(pId);

		Favorite parentFavorite = null;
		try {
			parentFavorite = this.getEntity(pId);
			if (parentFavorite != null) { // 检查父对象是否存在
				if (ObjectUtils.equals(parentFavorite.getAppId(), appId) && ObjectUtils.equals(parentFavorite.getUserId(), userId)) {
					return countEntitys(LIST_DIRECTORY_PID_ID, pId);
				} else {
					throw new FavoriteServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own favorite");// 移动的不是自己的收藏夹
				}
			} else {
				throw new FavoriteServiceException(ServiceError.ERROR_NOT_FOUND, "don't find the parent favorite by id " + pId);
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteServiceException(e);
		}
	}

	@Override
	public List<Favorite> getFavoritesForRoot(Long appId, Long userId, Long favoriteTypeId) throws FavoriteServiceException {
		// check parameter
		ServiceError.assertAppIdForFavorite(appId);
		ServiceError.assertUserIdForFavorite(userId);
		ServiceError.assertFavoriteTypeIdForFavorite(favoriteTypeId);

		try {
			List<Favorite> directories = this.getSubEntitys(LIST_DIRECTORY_ID_APPID_USERID_TYPEID_PID, 0, 500, appId, userId, favoriteTypeId, 0l);
			return directories;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteServiceException(e);
		}

	}

	@Override
	public int countFavoritesForRoot(Long appId, Long userId, Long favoriteTypeId) throws FavoriteServiceException {
		// check parameter
		ServiceError.assertAppIdForFavorite(appId);
		ServiceError.assertUserIdForFavorite(userId);
		ServiceError.assertFavoriteTypeIdForFavorite(favoriteTypeId);

		try {
			return this.countEntitys(LIST_DIRECTORY_ID_APPID_USERID_TYPEID_PID, 0, 500, appId, userId, favoriteTypeId, 0l);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new FavoriteServiceException(e);
		}
	}

	/**
	 * 
	 * @param favorite
	 * @return
	 */
	private String getParentNumberCode(Favorite favorite) {
		String[] parentIds = null;
		if (favorite != null) {
			parentIds = favorite.getNumberCode().split("-");
			if (parentIds != null && parentIds.length >= 4) {
				parentIds = Arrays.copyOfRange(parentIds, parentIds.length - 4, parentIds.length);
			}
		}
		String parentNumberCode = parentIds == null ? "" : StringUtils.join(parentIds, "-");
		return parentNumberCode;

	}

	/**
	 * 检查是否重名
	 * 
	 * @param appId
	 * @param userId
	 * @param pId
	 * @param name
	 * @throws FavoriteServiceException
	 */

	private void assertDuplicateName(List<Favorite> favorites, Favorite favorite) throws FavoriteServiceException {
		if (!CollectionUtils.isEmpty(favorites)) {
			for (Favorite dir : favorites) {
				if (dir != null && !ObjectUtils.equals(dir.getId(), favorite.getId()) && ObjectUtils.equals(dir.getName(), favorite.getName())) {
					throw new FavoriteServiceException(ServiceError.ERROR_DUPLICATE, "the " + favorite.getName() + " already exists");
				}
			}
		}
	}
}
