package com.ginkgocap.parasol.associate.test;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.associate.exception.AssociateTypeServiceException;
import com.ginkgocap.parasol.associate.model.AssociateType;
import com.ginkgocap.parasol.associate.service.AssociateTypeService;

public class AssociateTypeServiceTest extends TestBase implements Test {
	public static Long System_AppId = 7647448850l;

	@Autowired
	private AssociateTypeService associateTypeService;

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
	public void testAssociateType() throws AssociateTypeServiceException {

		// 保存
		String[] catagorys = new String[] { "人脉", "组织", "需求", "知识" };
		for (int i = 0; i < catagorys.length; i++) {
			System.out.println(catagorys[i]);
			AssociateType associateType = new AssociateType();
			associateType.setAppId(System_AppId);
			associateType.setName(catagorys[i]);
			Long id = associateTypeService.createAssociateType(System_AppId, associateType);
			Assert.assertNotNull(id);
		}

//		// 测试查询列表
//		List<AssociateType> associateTypes = associateTypeService.getAssociateTypessByAppId(System_AppId);
//		Assert.assertTrue(CollectionUtils.isNotEmpty(associateTypes));
//		;
//		for (AssociateType associateType : associateTypes) {
//			System.out.println(associateType);
//			// 测试单个类型
//			AssociateType only = associateTypeService.getAssociateType(associateType.getAppId(), associateType.getId());
//			System.out.println(only);
//			Assert.assertNotNull(only);
//
//			// 修改名称
//			only.setName(only.getName() + "bak");
//			boolean b = associateTypeService.updateAssociateType(only.getAppId(), associateType);
//			Assert.assertTrue(b);
//
//			// 根据名字查询
//			AssociateType nameAssociateType = associateTypeService.getAssociateTypeByName(only.getAppId(), only.getName());
//			Assert.assertNotNull(nameAssociateType);
//			System.out.println(nameAssociateType);
//			// 删除
//			associateTypeService.removeAssociateType(only.getAppId(), associateType.getId());
//		}

	}

}
