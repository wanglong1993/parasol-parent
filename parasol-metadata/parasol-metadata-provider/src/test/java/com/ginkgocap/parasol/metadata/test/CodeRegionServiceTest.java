package com.ginkgocap.parasol.metadata.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;

import com.alibaba.dubbo.common.json.ParseException;
import com.ginkgocap.parasol.metadata.exception.CodeRegionServiceException;
import com.ginkgocap.parasol.metadata.model.CodeRegion;
import com.ginkgocap.parasol.metadata.service.CodeRegionService;
import com.ginkgocap.parasol.metadata.type.CodeRegionType;

public class CodeRegionServiceTest extends TestBase implements Test {
	private static Logger logger = Logger.getLogger(CodeRegionServiceTest.class);
	private static Map<String, List<CodeRegion>> mapCodeRetion = new HashMap<String, List<CodeRegion>>();

	@Resource
	private CodeRegionService codeRegionService;

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
	public void testSave() throws IOException, ParseException, CodeRegionServiceException {
		// 保存省份数据
		List<CodeRegion> codeRegions = DataImport.getProvs();

		// 保存城市
		List<CodeRegion> townCodeRegions = DataImport.getTowns();
		for (CodeRegion codeRegion : townCodeRegions) {
			String tbId = codeRegion.getTbParentId();
			List<CodeRegion> regions = mapCodeRetion.get(tbId);
			if (regions == null) {
				regions = new ArrayList<CodeRegion>();
				mapCodeRetion.put(tbId, regions);
			}
			regions.add(codeRegion);
		}

		Assert.assertTrue(CollectionUtils.isNotEmpty(codeRegions));
		for (CodeRegion codeRegion : codeRegions) {
			codeRegionService.createCodeRegionForRoot(codeRegion, CodeRegionType.TYPE_CHINAINLAND); // 保存省数据
			// 保存城市数据
			saveCodeRegion(codeRegion.getTbId(), mapCodeRetion);
		}

		
		//保存港澳
		List<CodeRegion> gangaoCodeRegions = DataImport.getHkAomeng();
		mapCodeRetion.clear();
		for (CodeRegion codeRegion : gangaoCodeRegions) {
			String tbId = codeRegion.getTbParentId();
			List<CodeRegion> regions = mapCodeRetion.get(tbId);
			if (regions == null) {
				regions = new ArrayList<CodeRegion>();
				mapCodeRetion.put(tbId, regions);
			}
			regions.add(codeRegion);
		}
		
		List<CodeRegion> rootCodes = mapCodeRetion.get("1");
		if (CollectionUtils.isNotEmpty(rootCodes)) {
			for (CodeRegion codeRegion : rootCodes) {
				codeRegionService.createCodeRegionForRoot(codeRegion, CodeRegionType.TYPE_GANGAOTAI); // 保存省数据
				// 保存城市数据
				saveCodeRegion(codeRegion.getTbId(), mapCodeRetion);
			}
		}
		
		
		
		//保存台湾
		List<CodeRegion> taiwanCodeRegions = DataImport.getTaiwan();
		mapCodeRetion.clear();
		for (CodeRegion codeRegion : taiwanCodeRegions) {
			String tbId = codeRegion.getTbParentId();
			List<CodeRegion> regions = mapCodeRetion.get(tbId);
			if (regions == null) {
				regions = new ArrayList<CodeRegion>();
				mapCodeRetion.put(tbId, regions);
			}
			regions.add(codeRegion);
		}
		
		List<CodeRegion> taiwangCodes = mapCodeRetion.get("2");
		if (CollectionUtils.isNotEmpty(taiwangCodes)) {
			for (CodeRegion codeRegion : taiwangCodes) {
				codeRegionService.createCodeRegionForRoot(codeRegion, CodeRegionType.TYPE_TAIWAN); // 保存省数据
				// 保存城市数据
				saveCodeRegion(codeRegion.getTbId(), mapCodeRetion);
			}
		}
		
		
		//保存马来西亚
		List<CodeRegion> CodeRegions = DataImport.getManlaixiya();
		mapCodeRetion.clear();
		for (CodeRegion codeRegion : CodeRegions) {
			String tbId = codeRegion.getTbParentId();
			List<CodeRegion> regions = mapCodeRetion.get(tbId);
			if (regions == null) {
				regions = new ArrayList<CodeRegion>();
				mapCodeRetion.put(tbId, regions);
			}
			regions.add(codeRegion);
		}
		
		List<CodeRegion> mlxyCityCodeRegion = mapCodeRetion.get("125");
		if (CollectionUtils.isNotEmpty(mlxyCityCodeRegion)) {
			for (CodeRegion codeRegion : mlxyCityCodeRegion) {
				codeRegionService.createCodeRegionForRoot(codeRegion, CodeRegionType.TYPE_MALAYSIA); // 保存省数据
				// 保存城市数据
				saveCodeRegion(codeRegion.getTbId(), mapCodeRetion);
			}
		}
		
		
		

		
		
		//保存海外
		List<CodeRegion> haiWaiCodeRegions = DataImport.getHaiWai();
		mapCodeRetion.clear();
		for (CodeRegion codeRegion : haiWaiCodeRegions) {
			String tbId = codeRegion.getTbParentId();
			List<CodeRegion> regions = mapCodeRetion.get(tbId);
			if (regions == null) {
				regions = new ArrayList<CodeRegion>();
				mapCodeRetion.put(tbId, regions);
			}
			regions.add(codeRegion);
		}
		
		if (CollectionUtils.isNotEmpty(haiWaiCodeRegions)) {
			for (CodeRegion codeRegion : haiWaiCodeRegions) {
				codeRegionService.createCodeRegionForRoot(codeRegion, CodeRegionType.TYPE_FOREIGNCOUNTRY); // 保存省数据
				// 保存城市数据
				//saveCodeRegion(codeRegion.getTbId(), mapCodeRetion);
			}
		}		
		
		
		
		
		
		
		
		
	}

	private void saveCodeRegion(String tbParentId, Map<String, List<CodeRegion>> map) throws CodeRegionServiceException {
		Long id = codeRegionService.getCodeRegionIdByTbId(tbParentId);
		if (id != null) {
			List<CodeRegion> codeRegions = map.get(tbParentId);
			if (CollectionUtils.isNotEmpty(codeRegions)) {
				for (CodeRegion codeRegion : codeRegions) {
					codeRegionService.createCodeRegionForChildren(id, codeRegion);
					saveCodeRegion(codeRegion.getTbId(), map);
				}
			}
		}

	}

}
