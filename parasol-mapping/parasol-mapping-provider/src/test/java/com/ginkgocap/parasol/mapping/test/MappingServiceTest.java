package com.ginkgocap.parasol.mapping.test;

import junit.framework.Test;
import junit.framework.TestResult;

import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.mapping.enumtype.MappingType;
import com.ginkgocap.parasol.mapping.exception.MappingServiceException;
import com.ginkgocap.parasol.mapping.service.MappingService;

public class MappingServiceTest extends TestBase implements Test {
	private static long System_AppId = 1;
	private static Long userId = 111l;
	private static long tagType = 1l; // 表示是什么类型，比如万能插座中的知识、事物等

	private static Long sourceId = 1l; // 资源的Id
	private static int sourceType = 1; // 资源的类型

	@Autowired
	private MappingService mappingService;

	@Override
	public int countTestCases() {
		return 0;
	}

	@Override
	public void run(TestResult result) {

	}

	@org.junit.Test
	public void testSave() throws MappingServiceException {
		//新建一个万能插座的用户
		mappingService.saveMapping(null, 44l, MappingType.USER);
		//新建万能插座用户44l和开放平台4l的对应关系
		mappingService.saveMapping(4l, 44l, MappingType.USER);
		//新建开放平台4l用户
		mappingService.saveMapping(4l, null, MappingType.USER);
		


		
		//新建一个万能插座的用户
		mappingService.saveMapping(null, 66l, MappingType.USER);
		//新建开放平台4l用户
		mappingService.saveMapping(6l, null, MappingType.USER);
		mappingService.saveMapping(6l, 66l, MappingType.USER);

		System.out.println(mappingService.getOpenId(44l, MappingType.USER));
		System.out.println(mappingService.getUid(4l, MappingType.USER));
		System.out.println(mappingService.getUid(5l, MappingType.USER));

	}
}
