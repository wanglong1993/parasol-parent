package com.ginkgocap.parasol.file.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.ginkgocap.parasol.file.model.FileIndex;


public class FileIndexServiceTest extends TestBase{
	
	@Resource
	private FileIndexService fileIndexService;
	
	@Test
	public void TestInsertFileIndex() {
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
		fileIndexService.insert(fileIndex);
	}
	
}
