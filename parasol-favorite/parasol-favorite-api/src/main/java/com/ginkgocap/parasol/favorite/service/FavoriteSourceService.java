package com.ginkgocap.parasol.favorite.service;

import java.util.List;

import com.ginkgocap.parasol.favorite.exception.FavoriteServiceException;
import com.ginkgocap.parasol.favorite.exception.FavoriteSourceServiceException;
import com.ginkgocap.parasol.favorite.model.FavoriteSource;

/**
 * 
 * 
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午1:10:30
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface FavoriteSourceService {

	/**
	 * 创建一个收藏下的资源 注意依赖条件：收藏必须存在。
	 * 
	 * @param FavoriteSource
	 * @return Long
	 * 
	 * @throws FavoriteServiceException
	 */
	public Long createFavoriteSources(FavoriteSource sources) throws FavoriteSourceServiceException;

	/**
	 * 删除收藏下的一个资源 小心使用
	 * @param appId
	 * @param userId
	 * @param id
	 * @return
	 * @throws FavoriteSourceServiceException
	 */
	public boolean removeFavoriteSources(Long appId, Long userId, Long id) throws FavoriteSourceServiceException;

	/**
	 * 从资源中是删除具有资源Id的所有收藏资源 小心使用
	 * 
	 * @param appId
	 * @param userId
	 * @param sourceId : FavoriteSource.sourceId
	 * @return
	 * @throws FavoriteServiceException
	 */
	public boolean removeFavoriteSourcesBySourceId(long userId, Long appId, int sourceType, Long sourceId) throws FavoriteSourceServiceException;
	
	
	
	/**
	 * 从资源中是查询具有资源Id的所有收藏资源
	 * Example: 一篇知识都是在什么收藏下；一个文件都是在什么组织下
	 * @param appId
	 * @param userId
	 * @param sourceType
	 * @param sourceId 业务数据的Id(FavoriteSource.sourceId)
	 * @param sourceId : 
	 * @return
	 * @throws FavoriteServiceException
	 */
	public List<FavoriteSource> getFavoriteSourcesBySourceId(long userId, Long appId, int sourceType, Long sourceId) throws FavoriteSourceServiceException;
	
	
	/**
	 * 改变资源所在的收藏
	 * @param userId
	 * @param appId
	 * @param favoriteId
	 * @param ids
	 * @return
	 * @throws FavoriteSourceServiceException
	 */
	public boolean moveFavoriteSources(long userId, long appId, Long favoriteId , Long[] ids) throws FavoriteSourceServiceException;

	

	/**
	 * 更新 favoriteSource
	 * 
	 * @param favoriteSource
	 * @return
	 * @throws FavoriteSourceServiceException
	 */
	public boolean updateFavoriteSource(FavoriteSource favoriteSource) throws FavoriteSourceServiceException;

	/**
	 * 查询一个Favorite
	 * 
	 * @param id
	 * @return
	 * @throws FavoriteServiceException
	 */
	public FavoriteSource getFavoriteSourceById(Long appId, Long id) throws FavoriteSourceServiceException;

	/**
	 * 查询一个收藏下的资源列表
	 * 
	 * @param favoriteId
	 * @return
	 * @throws FavoriteSourceServiceException
	 */
	public List<FavoriteSource> getFavoriteSourcesByFavoriteId(Long appId, Long userId, Long favoriteId) throws FavoriteSourceServiceException;

	/**
	 * 统计收藏下的资源数量
	 * 
	 * @param appId
	 * @return
	 * @throws FavoriteSourceServiceException
	 */
	public int countFavoriteSourcesByFavoriteId(Long appId, Long userId, Long favoriteId) throws FavoriteSourceServiceException;

	/**
	 * 查询某个资源是否在指定的收藏下，存在就返回这个FavoriteSource，否则, 返回 null
	 * 
	 * @param userId
	 * @param appId
	 * @param favoriteId
	 * @param sourceType
	 * @param souriceId
	 * @return
	 * @throws FavoriteSourceServiceException
	 */
	public FavoriteSource getFavoriteSource(Long userId, Long favoriteId, Long sourceAppId, Integer sourceType, Long souriceId) throws FavoriteSourceServiceException;

}
