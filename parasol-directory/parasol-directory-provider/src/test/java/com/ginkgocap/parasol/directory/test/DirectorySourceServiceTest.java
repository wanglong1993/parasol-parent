package com.ginkgocap.parasol.directory.test;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.exception.DirectorySourceServiceException;
import com.ginkgocap.parasol.directory.exception.DirectoryTypeServiceException;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.model.DirectorySource;
import com.ginkgocap.parasol.directory.model.DirectoryType;
import com.ginkgocap.parasol.directory.service.DirectoryService;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;
import com.ginkgocap.parasol.directory.service.DirectoryTypeService;

import junit.framework.Test;
import junit.framework.TestResult;

public class DirectorySourceServiceTest extends TestBase implements Test {
	private static Long System_AppId = 1l;
	private static Long user_id = 111l;
	private static int Source_type = 1;
	private static Long source_Id = 1l;
	@Autowired
	private DirectoryService directoryService;
	@Autowired
	private DirectoryTypeService directoryTypeService;
	@Autowired
	private DirectorySourceService directorySourceService;

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
	public void testDirectorySource() throws DirectoryTypeServiceException, DirectoryServiceException, DirectorySourceServiceException {
		// 先用DirectoryTypeTest@testDirectoryType，创建分类
		// 再用DirectorySource@testSaveGetDirectory创建分类下边的目录

		// 应用-》分类
		DirectoryType directoryType = directoryTypeService.getDirectoryTypeByName(System_AppId, "组织");
		Assert.assertNotNull(directoryType);
		System.out.println(directoryType);

		// 应用-》分类-》根目录
		List<Directory> rootDirectories = directoryService.getDirectorysForRoot(System_AppId, user_id, directoryType.getId());
		//Assert.assertTrue(CollectionUtils.isNotEmpty(rootDirectories));
		
		Object[] parameter = new Object[]{user_id, System_AppId, 1, 3913468139012241L};
		List<DirectorySource> souceList = directorySourceService.getSourcesByDirectoryIdAndSourceType(1,2, parameter);
		Assert.assertTrue(souceList != null && souceList.size() > 0);
		System.err.println(souceList);
		
		//测试创建
		for (Directory directory : rootDirectories) {
			Assert.assertNotNull(directory);
			List<Directory> subDirectorys = directoryService.getDirectorysByParentId(System_AppId, user_id, directory.getId());
			if (CollectionUtils.isNotEmpty(subDirectorys)) {
				Directory targetDirectory = subDirectorys.get(0);
				DirectorySource source = new DirectorySource();
				source.setUserId(user_id);
				source.setAppId(System_AppId);
				source.setSourceType(Source_type);
				source.setSourceId(source_Id);
				source.setDirectoryId(targetDirectory.getId());
				directorySourceService.createDirectorySources(source);
			}
		}

		//测试查询
		Long sourceId = null;
		for (Directory directory : rootDirectories) {
			Assert.assertNotNull(directory);
			List<Directory> subDirectorys = directoryService.getDirectorysByParentId(System_AppId, user_id, directory.getId());
			if (CollectionUtils.isNotEmpty(subDirectorys)) {
				Directory targetDirectory = subDirectorys.get(0);
				List<DirectorySource> directorySources = directorySourceService.getDirectorySourcesByDirectoryId(System_AppId, user_id, targetDirectory.getId());
				Assert.assertTrue(CollectionUtils.isNotEmpty(directorySources));
				for (DirectorySource directorySource : directorySources) {
					System.out.println(directorySource);
					//测试更新
					directorySource.setSourceTitle("update title");
					directorySourceService.updateDirectorySource(directorySource);

					//测试单个查询
					DirectorySource dbDirectorySource = directorySourceService.getDirectorySourceById(System_AppId, directorySource.getId());
					System.out.println(dbDirectorySource);
					Assert.assertEquals(dbDirectorySource.getSourceTitle(), "update title");
					sourceId = dbDirectorySource.getSourceId();
					
					//测试单个删除
					//directorySourceService.removeDirectorySources(dbDirectorySource.getId());
				}
				

				
			}
		}
		
		
		
		
//		//测试清楚SourceId
//		if (sourceId != null) {
//			directorySourceService.removeDirectorySourcesBySourceId(user_id, System_AppId, Source_type, sourceId);
//		}

	}

}
