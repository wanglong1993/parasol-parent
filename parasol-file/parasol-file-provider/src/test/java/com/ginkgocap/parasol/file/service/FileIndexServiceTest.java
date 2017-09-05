package com.ginkgocap.parasol.file.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ginkgocap.parasol.file.exception.FileIndexServiceException;
import com.ginkgocap.parasol.file.model.FileIndex;


public class FileIndexServiceTest extends TestBase{
	
	@Resource
	private FileIndexService fileIndexService;
	
	@Test
	public void TestInsertFileIndex() throws FileIndexServiceException {
		FileIndex fileIndex = new FileIndex();
		fileIndex.setCrc32("d26bc49a");
		fileIndex.setCreaterId(1l);
		fileIndex.setFilePath("/xxx/xxxx/xxx/flow.png");
		fileIndex.setFileSize(10000l);
		fileIndex.setFileTitle("junit 测试");
		fileIndex.setFileType(1);
		fileIndex.setMd5("297538328379234850175219338316847563186");
		fileIndex.setModuleType(3);
		fileIndex.setServerHost("http://gtpic01:8181");
		fileIndex.setStatus(1);
		fileIndex.setTaskId("MTIxMDEwMTkyOTU2NTU3Z3VveXVhbnl1YW45OTYwMTE=");
		fileIndex.setThumbnailsPath("/xx/xx/xx/flow.png");
		fileIndex.setTranscoding(0);
		fileIndex.setAppId(111111);
		fileIndexService.insertFileIndex(fileIndex);
	}
	
	@Test
	public void TestGetFileIndexById() throws FileIndexServiceException {
		long id = 3915190764830725l;
		FileIndex file = fileIndexService.getFileIndexById(id);
		System.out.println("file name = "+file.getFileTitle());
	}
	
	@Test
	public void TestDeleteFileById() throws FileIndexServiceException {
		long id = 3915182137147397l;
		boolean flag = fileIndexService.deleteFileIndexById(1,id);
		System.out.println("flag ==="+flag);
	}
	
	@Test
	public void TestGetFileIndexesByTaskId() throws FileIndexServiceException {
		String taskId = "MTIxMDEwMTkyOTU2NTU3Z3VveXVhbnl1YW45OTYwMTE=";
		List<FileIndex> files = fileIndexService.getFileIndexesByTaskId(taskId);
		System.out.println("files.size ===="+files.size());
	}
	
	@Test
	public void TestGetFileIndexesByUserId() throws FileIndexServiceException {
		long createrId = 1l;
		List<FileIndex> files = fileIndexService.getFileIndexesByCreaterId(createrId);
		System.out.println("get FileIndexes by UserId "+files.size());
	}
	
	@Test
	public void TestUpdateFileIndex() throws FileIndexServiceException {
		long id = 3915194929774597l;
		String fileTitle = "测试修改附件名称";
		FileIndex file = fileIndexService.updateFileIndexByFileTitle(id, fileTitle);
		System.out.println("update file index title "+ file.getFileTitle());
	}
}
