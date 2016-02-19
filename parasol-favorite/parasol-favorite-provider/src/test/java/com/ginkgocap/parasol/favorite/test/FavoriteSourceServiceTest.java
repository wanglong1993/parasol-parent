package com.ginkgocap.parasol.favorite.test;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.favorite.exception.FavoriteServiceException;
import com.ginkgocap.parasol.favorite.exception.FavoriteSourceServiceException;
import com.ginkgocap.parasol.favorite.exception.FavoriteTypeServiceException;
import com.ginkgocap.parasol.favorite.model.Favorite;
import com.ginkgocap.parasol.favorite.model.FavoriteSource;
import com.ginkgocap.parasol.favorite.model.FavoriteType;
import com.ginkgocap.parasol.favorite.service.FavoriteService;
import com.ginkgocap.parasol.favorite.service.FavoriteSourceService;
import com.ginkgocap.parasol.favorite.service.FavoriteTypeService;

import junit.framework.Test;
import junit.framework.TestResult;

public class FavoriteSourceServiceTest extends TestBase implements Test {
	private static Long System_AppId = 1l;
	private static Long user_id = 111l;
	private static int Source_type = 1;
	private static Long source_Id = 1l;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private FavoriteTypeService favoriteTypeService;
	@Autowired
	private FavoriteSourceService favoriteSourceService;

	@Override
	public int countTestCases() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void run(TestResult result) {
		// TODO Auto-generated method stub
	}

	@org.junit.Test
	public void testFavoriteSource() throws FavoriteTypeServiceException, FavoriteServiceException, FavoriteSourceServiceException {
		// 先用FavoriteTypeTest@testFavoriteType，创建分类
		// 再用FavoriteSource@testSaveGetFavorite创建分类下边的目录

		// 应用-》分类
		FavoriteType favoriteType = favoriteTypeService.getFavoriteTypeByName(System_AppId, "组织");
		Assert.assertNotNull(favoriteType);
		System.out.println(favoriteType);

		// 应用-》分类-》根目录
		List<Favorite> rootDirectories = favoriteService.getFavoritesForRoot(System_AppId, user_id, favoriteType.getId());
		Assert.assertTrue(CollectionUtils.isNotEmpty(rootDirectories));

		//测试创建
		for (Favorite favorite : rootDirectories) {
			Assert.assertNotNull(favorite);
			List<Favorite> subFavorites = favoriteService.getFavoritesByParentId(System_AppId, user_id, favorite.getId());
			if (CollectionUtils.isNotEmpty(subFavorites)) {
				Favorite targetFavorite = subFavorites.get(0);
				FavoriteSource source = new FavoriteSource();
				source.setUserId(user_id);
				source.setAppId(System_AppId);
				source.setSourceType(Source_type);
				source.setSourceId(source_Id);
				source.setFavoriteId(targetFavorite.getId());
				favoriteSourceService.createFavoriteSources(source);
			}
		}

		//测试查询
		Long sourceId = null;
		for (Favorite favorite : rootDirectories) {
			Assert.assertNotNull(favorite);
			List<Favorite> subFavorites = favoriteService.getFavoritesByParentId(System_AppId, user_id, favorite.getId());
			if (CollectionUtils.isNotEmpty(subFavorites)) {
				Favorite targetFavorite = subFavorites.get(0);
				List<FavoriteSource> favoriteSources = favoriteSourceService.getFavoriteSourcesByFavoriteId(System_AppId, user_id, targetFavorite.getId());
				Assert.assertTrue(CollectionUtils.isNotEmpty(favoriteSources));
				for (FavoriteSource favoriteSource : favoriteSources) {
					System.out.println(favoriteSource);
					//测试更新
					favoriteSource.setSourceTitle("update title");
					favoriteSourceService.updateFavoriteSource(favoriteSource);

					//测试单个查询
					FavoriteSource dbFavoriteSource = favoriteSourceService.getFavoriteSourceById(System_AppId, favoriteSource.getId());
					System.out.println(dbFavoriteSource);
					Assert.assertEquals(dbFavoriteSource.getSourceTitle(), "update title");
					sourceId = dbFavoriteSource.getSourceId();
					
					//测试单个删除
					//favoriteSourceService.removeFavoriteSources(dbFavoriteSource.getId());
				}
				

				
			}
		}
		
		
		
		
//		//测试清楚SourceId
//		if (sourceId != null) {
//			favoriteSourceService.removeFavoriteSourcesBySourceId(user_id, System_AppId, Source_type, sourceId);
//		}

	}

}
