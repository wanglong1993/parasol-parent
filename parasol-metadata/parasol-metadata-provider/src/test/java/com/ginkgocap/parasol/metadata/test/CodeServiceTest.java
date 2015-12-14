package com.ginkgocap.parasol.metadata.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import com.ginkgocap.parasol.metadata.exception.CodeServiceException;
import com.ginkgocap.parasol.metadata.model.Code;
import com.ginkgocap.parasol.metadata.service.CodeService;

public class CodeServiceTest extends TestBase implements Test {
	private static String[] inputString = {
		// @formatter:off
		"IT行业	IT服务",
		"	电子/半导体",
		"	电子商务",
		"	互联网",
		"	计算机软件",
		"	计算机硬件",
		"	通信/通讯",
		"	游戏",
		"医疗	医药",
		"	医疗器械",
		"	医院/医疗机构",
		"金融	银行",
		"	证券/基金/期货",
		"	保险",
		"	投资",
		"教育	学前教育",
		"	初中等教育",
		"	高等教育",
		"	培训",
		"消费品	烟草品",
		"	鲜花/花艺/假花",
		"	食品/饮料",
		"	奢侈品/珠宝",
		"	日用品/化妆品",
		"	母婴用品",
		"	酒/茶叶",
		"	家具/家居",
		"	家电/数码产品",
		"	服装/纺织/鞋帽",
		"	办公用品",
		"制造业	造纸/印刷/包装",
		"	原油/能源",
		"	原材料/加工",
		"	新材料",
		"	汽车",
		"	摩托车/自行车",
		"	军工/国防",
		"	机械/自动化",
		"	化工 ",
		"	航天/造船",
		"	采矿/金属",
		"文体/传媒	广告/公关/会展",
		"	报纸/杂志/出版",
		"	广播/影视/电视",
		"	网络媒体",
		"	艺术/工艺",
		"	体育",
		"	动漫",
		"建筑/房地产	建筑设计/规划",
		"	土木工程",
		"	房地产",
		"	物业管理",
		"	建材/装修",
		"贸易流通	进出口",
		"	批发/零售",
		"	商店/超市",
		"	物流/仓储",
		"	运输/铁路/航空",
		"专业服务	法律",
		"	翻译",
		"	管理咨询",
		"	会计/审计",
		"	检测/认证",
		"	人力资源",
		"生活服务	酒店",
		"	餐饮",
		"	旅游",
		"	休闲/娱乐/健身",
		"	家政/社区服务",
		"	中介服务",
		"	美容/美发/美体SPA",
		"	摄影/婚庆",
		"政府/事业单位	工商",
		"	公安",
		"	公共事业",
		"	交通",
		"	教育",
		"	民政",
		"	市政",
		"	司法",
		"	医院",
		"	研究所/研究院",
		"其他	农/林/牧/渔",
		"	环境",
		"	非盈利组织",
		"	其他"};
		//@formatter:on
	private String[] roots = { "行业" }; // 根
	private String[] categories = { "IT行业", "专业服务", "其他", "制造业", "医疗", "建筑/房地产", "政府/事业单位", "教育", "文体/传媒", "消费品", "生活服务", "贸易流通", "金融" }; // 分类
	private String[] industries = { "工商业", "农林", "牧渔", "消费品", "房地产", "公用事业", "医药", "通信、媒体与科技", "能源", "矿产资源", "金融" }; // 行业

	private Map<String, List<String>> getDict() {
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
		String key = null;
		for (int i = 0; i < inputString.length; i++) {
			String line = inputString[i];
			String[] fileds = StringUtils.splitPreserveAllTokens(line, "\t");
			key = StringUtils.isEmpty(fileds[0]) ? key : fileds[0];
			List<String> subList = resultMap.get(key);
			String value = fileds[1];
			if (!line.startsWith("\t")) {
				subList = resultMap.get(key);
				if (subList == null) {
					subList = new ArrayList<String>();
					subList.add(value);
					resultMap.put(key, subList);
				}
				System.out.println(key);
			} else {
				if (subList != null) {
					subList.add(value);
				}
			}
		}

		System.out.println(resultMap.keySet());

		for (String strKey : resultMap.keySet()) {
			List<String> list = resultMap.get(strKey);
			for (String value : list) {
				System.out.println(strKey + ":" + value);
			}
		}

		// JsonObjectOutput jsonObjectOutput = new JsonObjectOutput(System.out);
		// try {
		//
		// jsonObjectOutput.writeObject(resultMap);
		// jsonObjectOutput.flushBuffer();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		return resultMap;
	}

	private List<List<String>> subList = new ArrayList<List<String>>();

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

	/**
	 * 导入数据到数据库
	 * 
	 */
	@org.junit.Test
	public void testImport() {
		try {
			// 保存根
			String name = "行业";
			Code code = new Code();
			code.setName(name);
			Long rootId = codeService.createCodeForRoot(code);
			Assert.assertTrue(rootId != null && rootId > 0l);
			Map<String, List<String>> result = getDict();
			for (String categoryName : result.keySet()) {
				// 保存分类
				code = new Code();
				code.setName(categoryName);
				Long categoryId = codeService.createCodeForChildren(rootId, code);
				Assert.assertTrue(categoryId != null && categoryId > 0l);
				List<String> industrie_list = result.get(categoryName);
				// 保存行业
				for (String industryName : industrie_list) {
					code = new Code();
					code.setName(industryName);
					Long industryId = codeService.createCodeForChildren(categoryId, code);
					Assert.assertTrue(industryId != null && industryId > 0l);
				}
			}

		} catch (CodeServiceException e) {
			e.printStackTrace();
		}
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
				Assert.assertTrue(rootId != null && rootId > 0l);
				// 保存分类
				for (int j = 0; j < categories.length; j++) {
					String categoryName = categories[j];

					code = new Code();
					code.setName(categoryName);
					Long categoryId = codeService.createCodeForChildren(rootId, code);
					Assert.assertTrue(categoryId != null && categoryId > 0l);

					// 保存行业
					for (int k = 0; k < industries.length; k++) {
						String industryName = industries[k];

						code = new Code();
						code.setName(industryName);
						Long industryId = codeService.createCodeForChildren(categoryId, code);
						Assert.assertTrue(industryId != null && industryId > 0l);

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
				// 分类
				List<Code> categories = codeService.getCodesByParentId(parentId, true);
				Assert.assertTrue(CollectionUtils.isNotEmpty(codes));
				for (Code category : categories) {
					Assert.assertNotNull(category);
					parentId = category.getId();
					// 行业
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

			// 设置不可用
			for (Code root : codes) {
				codeService.disabledCode(root.getId());
			}

			// 查询不可用得应该是空的
			codes = codeService.getCodesForRoot(false);
			Assert.assertFalse(CollectionUtils.isNotEmpty(codes));

			// 显示根
			codes = codeService.getCodesForRoot(true);
			Assert.assertTrue(CollectionUtils.isNotEmpty(codes));
			// 设置可用
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

			// 删除
			for (Code root : codes) {
				codeService.removeCode(root.getId());
			}

			// 查询不可用得应该是空的
			codes = codeService.getCodesForRoot(true);
			Assert.assertTrue(CollectionUtils.isEmpty(codes));

		} catch (CodeServiceException e) {
			e.printStackTrace();
		}
	}
}
