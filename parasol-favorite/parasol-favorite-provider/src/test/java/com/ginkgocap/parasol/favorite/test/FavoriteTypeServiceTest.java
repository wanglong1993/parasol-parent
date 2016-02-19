package com.ginkgocap.parasol.favorite.test;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.favorite.exception.FavoriteTypeServiceException;
import com.ginkgocap.parasol.favorite.model.FavoriteType;
import com.ginkgocap.parasol.favorite.service.FavoriteTypeService;

public class FavoriteTypeServiceTest extends TestBase implements Test {
	public static Long System_AppId = 1l;

	@Autowired
	private FavoriteTypeService favoriteTypeService;

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
	public void testFavoriteType() throws FavoriteTypeServiceException {

		// 保存
		String[] catagorys = new String[] { "人脉", "组织", "需求", "知识" };
		for (int i = 0; i < catagorys.length; i++) {
			System.out.println(catagorys[i]);
			FavoriteType favoriteType = new FavoriteType();
			favoriteType.setAppId(System_AppId);
			favoriteType.setName(catagorys[i]);
			Long id = favoriteTypeService.createFavoriteType(System_AppId, favoriteType);
			Assert.assertNotNull(id);
		}

//		// 测试查询列表
//		List<FavoriteType> favoriteTypes = favoriteTypeService.getFavoriteTypessByAppId(System_AppId);
//		Assert.assertTrue(CollectionUtils.isNotEmpty(favoriteTypes));
//		;
//		for (FavoriteType favoriteType : favoriteTypes) {
//			System.out.println(favoriteType);
//			// 测试单个类型
//			FavoriteType only = favoriteTypeService.getFavoriteType(favoriteType.getAppId(), favoriteType.getId());
//			System.out.println(only);
//			Assert.assertNotNull(only);
//
//			// 修改名称
//			only.setName(only.getName() + "bak");
//			boolean b = favoriteTypeService.updateFavoriteType(only.getAppId(), favoriteType);
//			Assert.assertTrue(b);
//
//			// 根据名字查询
//			FavoriteType nameFavoriteType = favoriteTypeService.getFavoriteTypeByName(only.getAppId(), only.getName());
//			Assert.assertNotNull(nameFavoriteType);
//			System.out.println(nameFavoriteType);
//			// 删除
//			favoriteTypeService.removeFavoriteType(only.getAppId(), favoriteType.getId());
//		}

	}

}
