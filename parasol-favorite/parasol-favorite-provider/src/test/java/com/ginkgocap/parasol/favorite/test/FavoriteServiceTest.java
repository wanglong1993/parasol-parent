package com.ginkgocap.parasol.favorite.test;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.favorite.exception.FavoriteServiceException;
import com.ginkgocap.parasol.favorite.exception.FavoriteTypeServiceException;
import com.ginkgocap.parasol.favorite.model.Favorite;
import com.ginkgocap.parasol.favorite.model.FavoriteType;
import com.ginkgocap.parasol.favorite.service.FavoriteService;
import com.ginkgocap.parasol.favorite.service.FavoriteTypeService;

import junit.framework.Test;
import junit.framework.TestResult;

public class FavoriteServiceTest extends TestBase implements Test {
	private static long System_AppId = 1;
	private static Long userId = 111l;

	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	private FavoriteTypeService favoriteTypeService;

	@Override
	public int countTestCases() {
		return 0;
	}

	@Override
	public void run(TestResult result) {

	}

	/**
	 * getFavoriteTypeByName getFavoritesForRoot getFavoritesByParentId
	 * createFavoriteForChildren createFavoriteForRoot
	 * 
	 * @throws FavoriteTypeServiceException
	 */
	@org.junit.Test
	public void testSaveGetFavorite() throws FavoriteTypeServiceException {
		FavoriteType favoriteType = favoriteTypeService.getFavoriteTypeByName(System_AppId, "组织");
		Assert.assertNotNull(favoriteType);
		String[] orgs = new String[] { "世界卫生组织", "世界气象组织", "国际劳工组织", "世贸组织" };

		for (int i = 0; i < orgs.length; i++) {
			Favorite favorite = new Favorite();
			favorite.setAppId(System_AppId);
			favorite.setUserId(userId); 
			favorite.setName(orgs[i]);
			favorite.setPid(0);
			favorite.setTypeId(favoriteType.getId());

			try {
				favoriteService.createFavoriteForRoot(favoriteType.getId(), favorite);
			} catch (FavoriteServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			String[] subs1 = new String[] { "a", "b", "c", "d", "e", "f", "g", "h" };
			List<Favorite> directories = favoriteService.getFavoritesForRoot(System_AppId, userId, favoriteType.getId());
			Assert.assertTrue(CollectionUtils.isNotEmpty(directories));

			for (Favorite favorite : directories) {
				Long pid = favorite.getId();
				for (int j = 0; j < subs1.length; j++) {
					Favorite subdir = new Favorite();
					subdir.setAppId(System_AppId);
					subdir.setUserId(userId);
					subdir.setName(subs1[j]);
					subdir.setPid(pid);
					subdir.setTypeId(favoriteType.getId());
					pid = favoriteService.createFavoriteForChildren(pid, subdir);
				}
				// 查询子目录
				List<Favorite> favorites = favoriteService.getFavoritesByParentId(favorite.getAppId(), favorite.getUserId(), favorite.getId());
				Assert.assertTrue(CollectionUtils.isNotEmpty(favorites));
				for (Favorite subDir : favorites) {
					System.out.println(subDir);
				}
			}

		} catch (FavoriteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	/**
	 * getFavoriteTypeByName
	 * @throws FavoriteTypeServiceException
	 */
	@org.junit.Test
	public void testDelete() throws FavoriteTypeServiceException {
		FavoriteType favoriteType = favoriteTypeService.getFavoriteTypeByName(System_AppId, "组织");
	
		try {
			List<Favorite> directories = favoriteService.getFavoritesForRoot(System_AppId, userId, favoriteType.getId());
			Assert.assertTrue(CollectionUtils.isNotEmpty(directories));

			for (Favorite favorite : directories) {
				favoriteService.removeFavorite(favorite.getAppId(), favorite.getUserId(), favorite.getId());
			}

		} catch (FavoriteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	/**
	 * getFavoriteTypeByName
	 * @throws FavoriteTypeServiceException
	 */
	@org.junit.Test
	public void testRenName() throws FavoriteTypeServiceException {
		FavoriteType favoriteType = favoriteTypeService.getFavoriteTypeByName(System_AppId, "组织");
	
		try {
			List<Favorite> directories = favoriteService.getFavoritesForRoot(System_AppId, userId, favoriteType.getId());
			Assert.assertTrue(CollectionUtils.isNotEmpty(directories));

			for (Favorite favorite : directories) {
				favorite.setName(favorite.getName()+"bak");
				favoriteService.updateFavorite(favorite.getAppId(), favorite.getUserId(), favorite);
			}
			
			
			directories = favoriteService.getFavoritesForRoot(System_AppId, userId, favoriteType.getId());
			Assert.assertTrue(CollectionUtils.isNotEmpty(directories));

			for (Favorite favorite : directories) {
				Assert.assertTrue(favorite.getName().endsWith("bak"));
			}

		} catch (FavoriteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
