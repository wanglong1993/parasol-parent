package com.ginkgocap.parasol.directory.test;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.directory.exception.DirectoryTypeServiceException;
import com.ginkgocap.parasol.directory.model.DirectoryType;
import com.ginkgocap.parasol.directory.service.DirectoryTypeService;

public class DirectoryTypeServiceTest extends TestBase implements Test {
	public static Long System_AppId = 1l;

	@Autowired
	private DirectoryTypeService directoryTypeService;

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
	public void testDirectoryType() throws DirectoryTypeServiceException {

		// 保存
		String[] catagorys = new String[] { "人脉", "组织", "需求", "知识" };
		for (int i = 0; i < catagorys.length; i++) {
			System.out.println(catagorys[i]);
			DirectoryType directoryType = new DirectoryType();
			directoryType.setAppId(System_AppId);
			directoryType.setName(catagorys[i]);
			Long id = directoryTypeService.createDirectoryType(System_AppId, directoryType);
			Assert.assertNotNull(id);
		}

//		// 测试查询列表
//		List<DirectoryType> directoryTypes = directoryTypeService.getDirectoryTypessByAppId(System_AppId);
//		Assert.assertTrue(CollectionUtils.isNotEmpty(directoryTypes));
//		;
//		for (DirectoryType directoryType : directoryTypes) {
//			System.out.println(directoryType);
//			// 测试单个类型
//			DirectoryType only = directoryTypeService.getDirectoryType(directoryType.getAppId(), directoryType.getId());
//			System.out.println(only);
//			Assert.assertNotNull(only);
//
//			// 修改名称
//			only.setName(only.getName() + "bak");
//			boolean b = directoryTypeService.updateDirectoryType(only.getAppId(), directoryType);
//			Assert.assertTrue(b);
//
//			// 根据名字查询
//			DirectoryType nameDirectoryType = directoryTypeService.getDirectoryTypeByName(only.getAppId(), only.getName());
//			Assert.assertNotNull(nameDirectoryType);
//			System.out.println(nameDirectoryType);
//			// 删除
//			directoryTypeService.removeDirectoryType(only.getAppId(), directoryType.getId());
//		}

	}

}
