package com.ginkgocap.parasol.metadata.test;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;

import junit.framework.Test;
import junit.framework.TestResult;

import com.ginkgocap.parasol.metadata.exception.CodeServiceException;
import com.ginkgocap.parasol.metadata.model.Code;
import com.ginkgocap.parasol.metadata.service.CodeService;

public class CodeServiceTest extends TestBase implements Test {

	private String[] roots = { "需求", "知识", "人脉", "组织", "会议" }; // 根
	private String[] categories = { "娱乐人物", "政治人物", "体育人物", "历史人物", "文化人物", "科学家", "虚拟人物", "行业人物", "话题人物", "其他人物" }; // 分类
	private String[] industries = { "工商业", "农林", "牧渔", "消费品", "房地产", "公用事业", "医药", "通信、媒体与科技", "能源", "矿产资源", "金融" }; // 行业

	@Resource
	private CodeService codeService;

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
	public void testSave() {
		try {

			// 保存根
			for (int i = 0; i < roots.length; i++) {
				String name = roots[i];

				Code code = new Code();
				code.setName(name);
				Long rootId = codeService.createCodeForRoot(code);
				Assert.assertTrue(rootId !=null && rootId > 0l);
				// 保存分类
				for (int j = 0; j < categories.length; j++) {
					String categoryName = categories[j];

					code = new Code();
					code.setName(categoryName);
					Long categoryId = codeService.createCodeForChildren(rootId, code);
					Assert.assertTrue(categoryId !=null && categoryId > 0l);

					//保存行业
					for (int k = 0; k < industries.length; k++) {
						String industryName = industries[k];

						code = new Code();
						code.setName(industryName);
						Long industryId = codeService.createCodeForChildren(categoryId, code);
						Assert.assertTrue(industryId !=null && industryId > 0l);

					}
				}
			}

		} catch (CodeServiceException e) {
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void testGet() {
		try {

			// 显示根
			List<Code> codes = codeService.getCodesForRoot(true);
			Assert.assertTrue(CollectionUtils.isNotEmpty(codes));
			
			for (Iterator iterator = codes.iterator(); iterator.hasNext();) {
				Code code = (Code) iterator.next();
				Long parentId = code.getId();
				//分类
				List<Code> categories = codeService.getCodesByParentId(parentId, true);
				Assert.assertTrue(CollectionUtils.isNotEmpty(codes));
				for (Code category : categories) {
					Assert.assertNotNull(category);
					parentId = category.getId();
					//行业
					List<Code> industries = codeService.getCodesByParentId(parentId, true);
					Assert.assertTrue(CollectionUtils.isNotEmpty(industries));
					
					for (Code industry : industries) {
						Assert.assertNotNull(industry);
						System.out.println(industry);
					}
				}
			}

		} catch (CodeServiceException e) {
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void testDisable() {
		try {
			// 显示根
			List<Code> codes = codeService.getCodesForRoot(true);
			Assert.assertTrue(CollectionUtils.isNotEmpty(codes));
			
			//设置不可用
			for (Code root : codes) {
				codeService.disabledCode(root.getId());
			}
			
			//查询不可用得应该是空的
			codes = codeService.getCodesForRoot(false);
			Assert.assertFalse(CollectionUtils.isNotEmpty(codes));
			

			
			
			// 显示根
			codes = codeService.getCodesForRoot(true);
			Assert.assertTrue(CollectionUtils.isNotEmpty(codes));
			//设置可用
			for (Code root : codes) {
				codeService.enabledCode(root.getId());
			}
			codes = codeService.getCodesForRoot(false);
			Assert.assertFalse(CollectionUtils.isEmpty(codes));


		} catch (CodeServiceException e) {
			e.printStackTrace();
		}
	}
	
	
	@org.junit.Test
	public void testDelete() {
		try {
			// 显示根
			List<Code> codes = codeService.getCodesForRoot(true);
			Assert.assertTrue(CollectionUtils.isNotEmpty(codes));
			
			//删除
			for (Code root : codes) {
				codeService.removeCode(root.getId());
			}
			
			//查询不可用得应该是空的
			codes = codeService.getCodesForRoot(true);
			Assert.assertTrue(CollectionUtils.isEmpty(codes));
	
		} catch (CodeServiceException e) {
			e.printStackTrace();
		}
	}
}
