package com.ginkgocap.parasol.favorite.service;

import java.util.List;

import com.ginkgocap.parasol.favorite.exception.FavoriteServiceException;
import com.ginkgocap.parasol.favorite.model.Favorite;

/**
 * 操作Favorite的接口定义
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:46:44
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface FavoriteService {

	/**
	 * 应用 用户 创建指定分类下的根收藏
	 * 
	 * @param appId
	 *            应用Id
	 * @param favoriteType
	 *            收藏分类
	 * @param favorite
	 * @return
	 * @throws FavoriteServiceException
	 */
	public Long createFavoriteForRoot(Long favoriteType, Favorite favorite) throws FavoriteServiceException;

	/**
	 * 应用 用户 创建指定父收藏下的收藏
	 * 
	 * @param appId
	 *            应用Id
	 * @param favoriteType
	 *            收藏分类
	 * @param favorite
	 * @return
	 * @throws FavoriteServiceException
	 */
	public Long createFavoriteForChildren(Long pId, Favorite favorite) throws FavoriteServiceException;

	/**
	 * 应用 用户 删除一个指定的收藏
	 * 也得删除子的收藏
	 * @param favoriteId
	 * @return
	 * @throws FavoriteServiceException
	 */
	public boolean removeFavorite(Long appId, Long userId, Long favoriteId) throws FavoriteServiceException;

	/**
	 * 应用 用户 更新一个指定的收藏 不能重名
	 * 
	 * @param favorite
	 * @return
	 * @throws FavoriteServiceException
	 */
	public boolean updateFavorite(Long appId, Long userId, Favorite favorite) throws FavoriteServiceException;

	/**
	 * 应用 用户 移动一个收藏 不能重名
	 * 
	 * @param favorite
	 * @return
	 * @throws FavoriteServiceException
	 */
	public boolean moveFavoriteToFavorite(Long appId, Long userId, Long favoriteId, Long toFavoriteId) throws FavoriteServiceException;

	/**
	 * 应用 用户 查询一个Favorite
	 * 
	 * @param favoriteId
	 * @return
	 * @throws FavoriteServiceException
	 */
	public Favorite getFavorite(Long appId, Long userId, Long id) throws FavoriteServiceException;

	/**
	 * 查询一个父节点下边的一级子节点 注意：最多返回500
	 * 
	 * @param parentId
	 *            父节点ID
	 * @param displayDisabled
	 *            是否显示禁用的Favorite，默认为false，不显示
	 * @return
	 * @throws FavoriteServiceException
	 */
	public List<Favorite> getFavoritesByParentId(Long appId, Long userId, Long pId) throws FavoriteServiceException;

	/**
	 * 查询一个父节点下边的一级子节点数量
	 * 
	 * @param appId
	 * @param userId
	 * @param pId
	 * @return
	 * @throws FavoriteServiceException
	 */
	public int countFavoritesByParentId(Long appId, Long userId, Long pId) throws FavoriteServiceException;

	/**
	 * 查询父节点的列表
	 * 注意：最多500个
	 * @param appId
	 * @param userId
	 * @param favoriteTypeId
	 * @return
	 * @throws FavoriteServiceException
	 */
	public List<Favorite> getFavoritesForRoot(Long appId, Long userId, Long favoriteTypeId) throws FavoriteServiceException;

	/**
	 * 查询父节点的总数
	 * 
	 * @param appId
	 * @param userId
	 * @param favoriteTypeId
	 * @return
	 * @throws FavoriteServiceException
	 */
	public int countFavoritesForRoot(Long appId, Long userId, Long favoriteTypeId) throws FavoriteServiceException;

}
