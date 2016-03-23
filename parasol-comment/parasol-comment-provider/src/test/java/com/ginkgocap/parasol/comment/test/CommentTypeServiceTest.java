package com.ginkgocap.parasol.comment.test;

import junit.framework.Test;
import junit.framework.TestResult;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.comment.exception.CommentTypeServiceException;
import com.ginkgocap.parasol.comment.model.CommentType;
import com.ginkgocap.parasol.comment.service.CommentTypeService;

public class CommentTypeServiceTest extends TestBase implements Test {
	public static Long System_AppId = 7647448850l;

	@Autowired
	private CommentTypeService commentTypeService;

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
	public void testCommentType() throws CommentTypeServiceException {

		// 保存
		String[] catagorys = new String[] { "人脉", "组织", "需求", "知识" };
		for (int i = 0; i < catagorys.length; i++) {
			System.out.println(catagorys[i]);
			CommentType commentType = new CommentType();
			commentType.setAppId(System_AppId);
			commentType.setName(catagorys[i]);
			Long id = commentTypeService.createCommentType(System_AppId, commentType);
			Assert.assertNotNull(id);
		}

//		// 测试查询列表
//		List<CommentType> commentTypes = commentTypeService.getCommentTypessByAppId(System_AppId);
//		Assert.assertTrue(CollectionUtils.isNotEmpty(commentTypes));
//		;
//		for (CommentType commentType : commentTypes) {
//			System.out.println(commentType);
//			// 测试单个类型
//			CommentType only = commentTypeService.getCommentType(commentType.getAppId(), commentType.getId());
//			System.out.println(only);
//			Assert.assertNotNull(only);
//
//			// 修改名称
//			only.setName(only.getName() + "bak");
//			boolean b = commentTypeService.updateCommentType(only.getAppId(), commentType);
//			Assert.assertTrue(b);
//
//			// 根据名字查询
//			CommentType nameCommentType = commentTypeService.getCommentTypeByName(only.getAppId(), only.getName());
//			Assert.assertNotNull(nameCommentType);
//			System.out.println(nameCommentType);
//			// 删除
//			commentTypeService.removeCommentType(only.getAppId(), commentType.getId());
//		}

	}

}
