package com.ginkgocap.parasol.favorite.service;

import java.util.List;

import com.ginkgocap.parasol.favorite.exception.FavoriteServiceException;
import com.ginkgocap.parasol.favorite.exception.FavoriteTypeServiceException;
import com.ginkgocap.parasol.favorite.model.FavoriteType;

/**
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午1:10:30
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface FavoriteTypeService {

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
	public Long createFavoriteType(Long appId, FavoriteType favoriteType) throws FavoriteTypeServiceException;

	/**
	 * 删除一个应用的分类
	 * 小心使用
	 * @param appId
	 * @param userId
	 * @param favoriteTypeId
	 * @return
	 * @throws FavoriteServiceException
	 */
	public boolean removeFavoriteType(Long appId, Long favoriteTypeId) throws FavoriteTypeServiceException;

	/**
	 * 应用 用户 更新一个指定的收藏 不能重名
	 * @param appId
	 * @param favoriteType
	 * @return
	 * @throws FavoriteTypeServiceException
	 */
	public boolean updateFavoriteType(Long appId, FavoriteType favoriteType) throws FavoriteTypeServiceException;

	/**
	 * 应用 用户 查询一个Favorite
	 * 
	 * @param favoriteId
	 * @return
	 * @throws FavoriteServiceException
	 */
	public FavoriteType getFavoriteType(Long appId, Long favoriteTypeId) throws FavoriteTypeServiceException;

	/**
	 * 查询一个父节点下边的一级子节点
	 * 注意：最多500
	 * @param parentId
	 *            父节点ID
	 * @param displayDisabled
	 *            是否显示禁用的Favorite，默认为false，不显示
	 * @return
	 * @throws FavoriteServiceException
	 */
	public List<FavoriteType> getFavoriteTypessByAppId(Long appId) throws FavoriteTypeServiceException;

	/**
	 * 查询一个App分类的数量
	 * @param appId
	 * @return
	 * @throws FavoriteTypeServiceException
	 */
	public int countFavoriteTypessByAppId(Long appId) throws FavoriteTypeServiceException;
	
	
	/**
	 * 通过名字查找一个FavoriteType对象
	 * @param appId
	 * @param name
	 * @return
	 * @throws FavoriteTypeServiceException
	 */
	public FavoriteType getFavoriteTypeByName(Long appId, String name) throws FavoriteTypeServiceException;



}
