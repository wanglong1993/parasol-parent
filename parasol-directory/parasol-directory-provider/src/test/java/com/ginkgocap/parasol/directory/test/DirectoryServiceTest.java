package com.ginkgocap.parasol.directory.test;

import java.util.List;

import com.ginkgocap.parasol.directory.model.Page;
import com.ginkgocap.parasol.directory.service.impl.ServiceError;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.exception.DirectoryTypeServiceException;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.model.DirectoryType;
import com.ginkgocap.parasol.directory.service.DirectoryService;
import com.ginkgocap.parasol.directory.service.DirectoryTypeService;

import junit.framework.Test;
import junit.framework.TestResult;

public class DirectoryServiceTest extends TestBase implements Test {
	private static long System_AppId = 1;
	private static Long userId = 111l;

	@Autowired
	private DirectoryService directoryService;

	@Autowired
	private DirectoryTypeService directoryTypeService;

	@Override
	public int countTestCases() {
		return 0;
	}

	@Override
	public void run(TestResult result) {

	}

	/**
	 * getDirectoryTypeByName getDirectorysForRoot getDirectorysByParentId
	 * createDirectoryForChildren createDirectoryForRoot
	 * 
	 * @throws DirectoryTypeServiceException
	 */
	@org.junit.Test
	public void testSaveGetDirectory() throws DirectoryTypeServiceException {
		DirectoryType directoryType = directoryTypeService.getDirectoryTypeByName(System_AppId, "组织");
		Assert.assertNotNull(directoryType);
		String[] orgs = new String[] { "世界卫生组织", "世界气象组织", "国际劳工组织", "世贸组织" };

		for (int i = 0; i < orgs.length; i++) {
			Directory directory = new Directory();
			directory.setAppId(System_AppId);
			directory.setUserId(userId); 
			directory.setName(orgs[i]);
			directory.setPid(0);
			directory.setTypeId(directoryType.getId());

			try {
				directoryService.createDirectoryForRoot(directoryType.getId(), directory);
			} catch (DirectoryServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			String[] subs1 = new String[] { "a", "b", "c", "d", "e", "f", "g", "h" };
			List<Directory> directories = directoryService.getDirectorysForRoot(System_AppId, userId, directoryType.getId());
			Assert.assertTrue(CollectionUtils.isNotEmpty(directories));

			for (Directory directory : directories) {
				Long pid = directory.getId();
				for (int j = 0; j < subs1.length; j++) {
					Directory subdir = new Directory();
					subdir.setAppId(System_AppId);
					subdir.setUserId(userId);
					subdir.setName(subs1[j]);
					subdir.setPid(pid);
					subdir.setTypeId(directoryType.getId());
					pid = directoryService.createDirectoryForChildren(pid, subdir);
				}
				// 查询子目录
				List<Directory> directorys = directoryService.getDirectorysByParentId(directory.getAppId(), directory.getUserId(), directory.getId());
				Assert.assertTrue(CollectionUtils.isNotEmpty(directorys));
				for (Directory subDir : directorys) {
					System.out.println(subDir);
				}
			}

		} catch (DirectoryServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@org.junit.Test
	public void testCreateRootDirectory() throws DirectoryServiceException {

		Directory directory = new Directory();
		directory.setAppId(System_AppId);
		directory.setName("根目录");
		directory.setPid(0);
		//directory.setNumberCode();
		directoryService.createDirectoryForRoot(7l, directory);
	}

	@org.junit.Test
	public void testCreateSubDirectory() throws DirectoryTypeServiceException {

	}

	
	/**
	 * getDirectoryTypeByName
	 * @throws DirectoryTypeServiceException
	 */
	@org.junit.Test
	public void testDelete() throws DirectoryTypeServiceException {
		DirectoryType directoryType = directoryTypeService.getDirectoryTypeByName(System_AppId, "组织");
	
		try {
			List<Directory> directories = directoryService.getDirectorysForRoot(System_AppId, userId, directoryType.getId());
			Assert.assertTrue(CollectionUtils.isNotEmpty(directories));

			for (Directory directory : directories) {
				directoryService.removeDirectory(directory.getAppId(), directory.getUserId(), directory.getId());
			}

		} catch (DirectoryServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	/**
	 * getDirectoryTypeByName
	 * @throws DirectoryTypeServiceException
	 */
	@org.junit.Test
	public void testRenName() throws DirectoryTypeServiceException {
		DirectoryType directoryType = directoryTypeService.getDirectoryTypeByName(System_AppId, "组织");
		try {

			List<Directory> directories = directoryService.getDirectorysForRoot(System_AppId, userId, directoryType.getId());
			Assert.assertTrue(CollectionUtils.isNotEmpty(directories));
			for (Directory directory : directories) {
				directory.setName(directory.getName()+"bak");
				directoryService.updateDirectory(directory.getAppId(), directory.getUserId(), directory);
			}
			
			
			directories = directoryService.getDirectorysForRoot(System_AppId, userId, directoryType.getId());
			Assert.assertTrue(CollectionUtils.isNotEmpty(directories));

			for (Directory directory : directories) {
				Assert.assertTrue(directory.getName().endsWith("bak"));
			}

		} catch (DirectoryServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@org.junit.Test
	public void testGetDirectoryName() throws DirectoryServiceException {

		try {
			Page<Directory> page = directoryService.getDirectoryName(13363l,"李", 7, 1, 6);
			//Assert.assertTrue(CollectionUtils.isNotEmpty(page.getList()));

			for (Directory directory : page.getList()) {
				ServiceError.assertDirectoryForDirectory(directory);
			}

		} catch (DirectoryServiceException e) {
			e.printStackTrace();
		}

	}
}
