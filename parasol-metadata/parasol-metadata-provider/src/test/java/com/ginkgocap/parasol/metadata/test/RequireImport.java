package com.ginkgocap.parasol.metadata.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.ginkgocap.parasol.metadata.exception.CodeServiceException;
import com.ginkgocap.parasol.metadata.model.Code;
import com.ginkgocap.parasol.metadata.service.CodeService;

/**
 * 需求数据导入
 * 
 * @author allenshen
 * @date 2016年3月31日
 * @time 上午10:16:55
 * @Copyright Copyright©2015 www.gintong.com
 */
public class RequireImport extends TestBase implements Test {
	@Resource
	private CodeService codeService;

	public static class DataStruct {
		private String key;
		private List<String> dataList = new ArrayList<String>();
		private boolean isRoot = false;

		public DataStruct(String key, boolean keyIsRoot, String... datas) {
			this.key = key;
			if (ArrayUtils.isNotEmpty(datas)) {
				for (int i = 0; i < datas.length; i++) {
					String value = datas[i];
					if (StringUtils.isNotBlank(value)) {
						dataList.add(datas[i]);
					}
				}
			}
			isRoot = keyIsRoot;

		}

		@Override
		public String toString() {
			return "DataStruct [key=" + key + ", dataList=" + dataList + ", isRoot=" + isRoot + "]";
		}
	}

	/**
	 * 需求的分类
	 * @throws CodeServiceException
	 */
	@org.junit.Test
	public void testImport() throws CodeServiceException {
		List<DataStruct> list = new ArrayList<RequireImport.DataStruct>();
		list.add(new DataStruct("需求", true, "需求", "服务"));
		list.add(new DataStruct("需求->需求", false, "金融", "教育", "医疗", "人力资源", "产品开发", "法律"));
		list.add(new DataStruct("需求->需求->金融", false, "投资", "融资"));
		list.add(new DataStruct("需求->需求->金融->投资", false, "股权","债权","项目","金融衍生产品及另类投资","艺术品"));
		list.add(new DataStruct("需求->需求->金融->融资", false, "股权","债权","项目","金融衍生产品及另类投资","艺术品"));

		list.add(new DataStruct("需求->需求->金融->投资->股权", false, "天使投资","风险投资","私募股权","股票投资","并购","借壳上市","配股","增发","上市配售","上市（ipo）"));
		list.add(new DataStruct("需求->需求->金融->投资->债权", false, "国债","公司债","地方债","短期及中期票据","结构化产品","民间借贷","信托产品","典当"));
		list.add(new DataStruct("需求->需求->金融->投资->项目", false, "房地产","矿权","林权","农场"));
		list.add(new DataStruct("需求->需求->金融->投资->金融衍生产品及另类投资", false, "黄金","外汇","商品期货","大宗实物","技术及专利","其他"));
		list.add(new DataStruct("需求->需求->金融->投资->艺术品", false, "中国书画","玉石器","家具","陶瓷","西画雕塑","古籍碑帖","邮品钱币","花卉","摄影","图书/报刊","工艺美术品","文房四宝","珠宝首饰","专题藏品","其他收藏品"));

		list.add(new DataStruct("需求->需求->金融->融资->股权", false, "天使投资","风险投资","私募股权","上市融资","配股","增发"));
		list.add(new DataStruct("需求->需求->金融->融资->债权", false, "地方债","公司债","可转换债券","短期及中期票据","民间借贷","金融机构贷款","结构化产品","融资租赁","担保","信托产品","典当"));
		list.add(new DataStruct("需求->需求->金融->融资->项目", false, "房地产","矿权","林权","农场"));
		list.add(new DataStruct("需求->需求->金融->融资->金融衍生产品及另类投资", false, "私募基金","黄金","外汇","商品期货","大宗实物","技术及专利","其他"));
		list.add(new DataStruct("需求->需求->金融->融资->艺术品", false, "中国书画","玉石器","家具","陶瓷","西画雕塑","古籍碑帖","邮品钱币","花卉","摄影","图书/报刊","工艺美术品","文房四宝","珠宝首饰","专题藏品","其他收藏品"));

		list.add(new DataStruct("需求->需求->教育", false, "找老师", "职业咨询"));
		list.add(new DataStruct("需求->需求->教育->找老师", false, "职业培训","艺术培训","在线教育","课外辅导","语言培训","移民留学","管理培训","公立学校","考试辅导","私立学校","其他"));
		list.add(new DataStruct("需求->需求->教育->职业咨询", false, "职场","互联网","行业","投资理财","创业","教育","艺术&兴趣","生活方式","其他"));

		list.add(new DataStruct("需求->需求->医疗", false, "找医生", "医疗咨询"));
		list.add(new DataStruct("需求->需求->医疗->找医生", false,"内科","外科","皮肤性病科","中医科","骨伤科","妇科","眼科","肿瘤及防治科","整形美容科","报告解读科","妇产科","营养科","儿科","耳鼻喉科","基因检测科","齿科","呼吸科","口腔颌面科","男科","精神心理科" ));
		list.add(new DataStruct("需求->需求->医疗->医疗咨询", false, "内科","外科","皮肤性病科","中医科","骨伤科","妇科","眼科","肿瘤及防治科","整形美容科","报告解读科","妇产科","营养科","儿科","耳鼻喉科","基因检测科","齿科","呼吸科","口腔颌面科","男科","精神心理科"));
		
		list.add(new DataStruct("需求->需求->人力资源", false, "职位招聘", "HR培训"));
		list.add(new DataStruct("需求->需求->产品开发", false, "项目", "外包"));
		list.add(new DataStruct("需求->需求->法律", false, "找律师", "法律咨询"));
		list.add(new DataStruct("需求->需求->法律->找律师", false, "婚姻家庭","刑事案件","知识产权","合同纠纷","公司法","劳动纠纷","房产纠纷","医疗事故","债务债权","其他"));
		list.add(new DataStruct("需求->需求->法律->法律咨询", false, "婚姻家庭","刑事案件","知识产权","合同纠纷","公司法","劳动纠纷","房产纠纷","医疗事故","债务债权","其他"));

		list.add(new DataStruct("需求->服务", false, "金融", "教育", "医疗", "人力资源", "产品开发", "法律"));
		list.add(new DataStruct("需求->服务->金融", false, "找投资", "找融资"));
		list.add(new DataStruct("需求->服务->金融->找投资", false, "股权","债权","项目","金融衍生产品及另类投资","艺术品"));
		list.add(new DataStruct("需求->服务->金融->找融资", false, "股权","债权","项目","金融衍生产品及另类投资","艺术品"));

		list.add(new DataStruct("需求->服务->金融->找投资->股权", false, "天使投资","风险投资","私募股权","股票投资","并购","借壳上市","配股","增发","上市配售","上市（ipo）"));
		list.add(new DataStruct("需求->服务->金融->找投资->债权", false, "国债","公司债","地方债","短期及中期票据","结构化产品","民间借贷","信托产品","典当"));
		list.add(new DataStruct("需求->服务->金融->找投资->项目", false, "房地产","矿权","林权","农场"));
		list.add(new DataStruct("需求->服务->金融->找投资->金融衍生产品及另类投资", false, "黄金","外汇","商品期货","大宗实物","技术及专利","其他"));
		list.add(new DataStruct("需求->服务->金融->找投资->艺术品", false, "中国书画","玉石器","家具","陶瓷","西画雕塑","古籍碑帖","邮品钱币","花卉","摄影","图书/报刊","工艺美术品","文房四宝","珠宝首饰","专题藏品","其他收藏品"));
		list.add(new DataStruct("需求->服务->金融->找融资->股权", false, "天使投资","风险投资","私募股权","上市融资","配股","增发"));
		list.add(new DataStruct("需求->服务->金融->找融资->债权", false, "地方债","公司债","可转换债券","短期及中期票据","民间借贷","金融机构贷款","结构化产品","融资租赁","担保","信托产品","典当"));
		list.add(new DataStruct("需求->服务->金融->找融资->项目", false, "房地产","矿权","林权","农场"));
		list.add(new DataStruct("需求->服务->金融->找融资->金融衍生产品及另类投资", false, "私募基金","黄金","外汇","商品期货","大宗实物","技术及专利","其他"));
		list.add(new DataStruct("需求->服务->金融->找融资->艺术品", false, "中国书画","玉石器","家具","陶瓷","西画雕塑","古籍碑帖","邮品钱币","花卉","摄影","图书/报刊","工艺美术品","文房四宝","珠宝首饰","专题藏品","其他收藏品"));

		list.add(new DataStruct("需求->服务->教育", false, "找老师", "职业咨询"));
		list.add(new DataStruct("需求->服务->教育->找老师", false, "职业培训","艺术培训","在线教育","课外辅导","语言培训","移民留学","管理培训","公立学校","考试辅导","私立学校","其他"));
		list.add(new DataStruct("需求->服务->教育->职业咨询", false, "职场","互联网","行业","投资理财","创业","教育","艺术&兴趣","生活方式","其他"));

		list.add(new DataStruct("需求->服务->医疗", false, "找医生", "医疗咨询"));
		list.add(new DataStruct("需求->服务->医疗->找医生", false,"内科","外科","皮肤性病科","中医科","骨伤科","妇科","眼科","肿瘤及防治科","整形美容科","报告解读科","妇产科","营养科","儿科","耳鼻喉科","基因检测科","齿科","呼吸科","口腔颌面科","男科","精神心理科" ));
		list.add(new DataStruct("需求->服务->医疗->医疗咨询", false, "内科","外科","皮肤性病科","中医科","骨伤科","妇科","眼科","肿瘤及防治科","整形美容科","报告解读科","妇产科","营养科","儿科","耳鼻喉科","基因检测科","齿科","呼吸科","口腔颌面科","男科","精神心理科"));

		list.add(new DataStruct("需求->服务->人力资源", false, "猎头", "HR培训"));
		list.add(new DataStruct("需求->服务->产品开发", false, "找项目", "找外包"));
		list.add(new DataStruct("需求->服务->法律", false, "找律师", "法律咨询"));
		list.add(new DataStruct("需求->服务->法律->找律师", false, "婚姻家庭","刑事案件","知识产权","合同纠纷","公司法","劳动纠纷","房产纠纷","医疗事故","债务债权","其他"));
		list.add(new DataStruct("需求->服务->法律->法律咨询", false, "婚姻家庭","刑事案件","知识产权","合同纠纷","公司法","劳动纠纷","房产纠纷","医疗事故","债务债权","其他"));
		
		list.add(new DataStruct("薪资范围", true, "3000元及以下/月","3000-7000元/月","7000-15000元/月","15000-30000元/月","30000-50000元/月","50000元以上/月","自定义金额范围"));
		list.add(new DataStruct("学历", true, "不限","本科及以上","硕士及以上","博士及以上"));
		list.add(new DataStruct("工作经历", true, "1年以下","1-3年","3-5年","5-10年","10年以上"));
		list.add(new DataStruct("职能", true, "IT互联网","文化传媒","通信","金融","学生","教育培训","医疗生物","政府科研NGO","司法法律","房产建筑","服务业","汽车摩托","轻工贸易","电子电气","机械重工","农林牧渔","光电新能源","物联网","化工环保","其它"));

		list.add(new DataStruct("职能->IT互联网", false, "开发","测试","运维/安全","硬件","产品","运营","设计","编辑/文档","游戏","市场BD公关","销售","客服/地服","行政文秘","财税审计","高管","风投风控","数据分析/BI","项目管理","采购物流","人力HR","法律/专利","IT咨询/培训","其它"));
		list.add(new DataStruct("职能->文化传媒", false, "经纪人","项目管理","艺术家/收藏","编辑记者","策划","创意/设计","动画原画","编导制作","公关/媒介","销售","市场","品牌","出版发行","高管","行政人事","财税审计","网站/APP","艺人/主持","其它"));
		list.add(new DataStruct("职能->通信", false, "软件/无线","硬件","质量/安全","系统集成","结构/设计","需求分析","项目管理","市场商务","采购物控","生产/IE","销售/售后","光通信研发","网络优化","增值业务","弱电安防","通信工程","技工普工","行政人事","法律","财务","高管","网站/APP","其它"));
		list.add(new DataStruct("职能->金融", false, "分析","交易","投资理财","金融产品","互联网产品","VC/PE","高管","设计","开发运维","测试","市场销售","人力行政","财会税务","融资租赁","客户服务","保险","担保信贷","拍卖典当","网站/AP"));
		list.add(new DataStruct("职能->学生", false, "管理/传播","工学","理学","经济学","法学","医学","农学/环保","教育学","文学","艺术","历史/哲学","职业技术","其它"));
		list.add(new DataStruct("职能->教育培训", false, "幼教","小学教师","初中教师","高中教师","大学教师","特岗教师","中专技校","艺术体育","科研/学者","课外辅导","教务/管理","招生","会计财务","后勤行政","市场销售","职业技能","留学移民","翻译出版","网站/APP","其它"));
		list.add(new DataStruct("职能->医疗生物", false, "内科医生","外科医生","专科医生","护理","辅诊/药剂","中医大夫","高管","生物工程","药品研发","注册认证","临床试验","药品生产","采购仓储","质量/安全","器械研发","设备维修","药店连锁","贸易批发","医学媒体","美容保健","宠物","医药代表","市场商务","行政后勤","财务","网站/APP","其它"));
		list.add(new DataStruct("职能->政府科研NGO", false, "公务员","科研","公检法","应届/实习","经营/项目","市场/品牌","网站/APP","财税审计","行政后勤","NGO/非盈利","其它"));
		list.add(new DataStruct("职能->司法法律", false, "专利/知识产权","律师","法务/法律顾问","经营管理","公检法","司法鉴定","公证/书记","调查/检验","行政人事","财税审计","网站/APP","其它"));
		list.add(new DataStruct("职能->房产建筑", false, "房产中介","施工安装","设计装修","监理造价","项目管理","安全质检","法律法务","水暖电气","行政后勤","开发/物业","建筑设计","规划设计","园林景观","市政路桥","钢结构","环境节能","市场营销","财会审计","高层管理","网站/APP","其它"));
		list.add(new DataStruct("职能->服务业", false, "酒店旅游","餐饮","客服/服务","会计财务","家政保洁","安保","市场销售","人力资源","商务服务","搬家物流","摄影婚庆","汽车","机电维修","建筑装修","房产物业","美容美体","娱乐健身","医疗保健","商超零售","租赁","人力后勤","网站/APP","其它"));
		list.add(new DataStruct("职能->汽车摩托", false, "设计研发","生产制造","质量检验","高级管理","装潢美容","维修客服","车险救援","财税审计","销售售后","采购物流","用品配件","司机代驾","汽车租赁","市场策划","法律法务","贸易进出口","行政人事","网站/APP","其它"));
		list.add(new DataStruct("职能->轻工贸易", false, "贸易进出口","销售分销","采购仓储物流","行政人事","维修客服","机电仪表","质检/认证","财税审计","市场策划","法律/专利","网站/推广","商贸百货","纺织服装","建材家居","珠宝首饰","食品饮料","包装印刷","工艺礼品","产品设计研发","网站/APP","其它"));
		list.add(new DataStruct("职能->电子电气", false, "自动化","电路电源","机电","电气研发","单片机","仪器仪表","质量安全","生产/IE","结构/工艺","网站/APP","采购物控","销售/售后","行政人事","硬件","传感器","照明LED","电器家电","技工普工","市场商务","法律","财务","高管","其它"));
		list.add(new DataStruct("职能->机械重工", false, "设计研发","生产/设备","机械机电","品质/质检","维修客服","采购仓储物流","工艺","项目管理","技工操作工","仪器仪表","销售分销","贸易进出口","财税审计","市场策划","法律法务","行政人事","翻译","网站/APP","高管","其它"));
		list.add(new DataStruct("职能->农林牧渔", false, "经营管理","技术员/设计师","兽医/饲养/养殖","机械设备","加工/质检","销售","采购仓储物流","行政人事","市场策划","财税审计","网站/APP","其它"));
		list.add(new DataStruct("职能->光电新能源", false, "高管","项目管理","电力/能源","水利水电","光伏太阳能","电气/自动化","电源电池照明","生产/设备","采购仓储物流","品质质检","销售分销","维修客服","财税审计","市场策划","法律法务","行政人事","网站/APP","其它"));
		list.add(new DataStruct("职能->物联网", false, "经营管理","光电/通信研发","传感器研发","云计算/云工程","应用软件","硬件研发","M2M","芯片研发","半导体照明","生物识别","RFID","移动支付","二维码","测试","项目管理/解决方案","销售","生产/质检","市场","财务","人力资源","行政后勤","网站/APP","应届/实习","其它"));
		list.add(new DataStruct("职能->化工环保", false, "油田技术","化工技术","煤炭/天然气","法律法务","环保/水处理","高管","项目管理","工艺/研发","测绘勘探","采矿冶炼","机械机电","仪器仪表","生产/设备管理","车间技工","采购仓储物流","品质/质检","销售分销","维修客服","贸易进出口","市场策划","法律法务","行政人事","翻译","网站/APP","其它"));
		list.add(new DataStruct("职能->其它", false, "其它"));
		
		
		
		Map<String, Long> idMaps = new HashMap<String, Long>();
		Long rootId = null;
		for (DataStruct dataStruct : list) {
			System.out.println(dataStruct);
			String name = dataStruct.key;

			if (dataStruct.isRoot) {
				Code code = new Code();
				code.setName(dataStruct.key);
				rootId = codeService.createCodeForRoot(code);
				idMaps.put(dataStruct.key, rootId);
			} else {
				rootId = idMaps.get(dataStruct.key);
			}

			List<String> data = dataStruct.dataList;
			if (CollectionUtils.isNotEmpty(data)) {
				for (String subName : data) {
					Code code = new Code();
					code.setName(subName);
					Long id = codeService.createCodeForChildren(rootId, code);
					if (id != null) {
						String key = name + "->" + subName;
						idMaps.put(key, id);
					}
				}
			}
		}
	}

	@Override
	public int countTestCases() {
		return 0;
	}

	@Override
	public void run(TestResult result) {
		
	}

}
