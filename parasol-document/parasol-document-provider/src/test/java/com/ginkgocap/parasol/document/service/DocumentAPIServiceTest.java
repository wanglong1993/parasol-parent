package com.ginkgocap.parasol.document.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ginkgocap.parasol.document.model.DocumentAPI;
import com.ginkgocap.parasol.document.model.DocumentRequest;

public class DocumentAPIServiceTest extends TestBase{
	
	@Resource
	private DocumentAPIService documentAPIService;
	
	@Test
	public void TestSaveDocumentAPI() {
		DocumentAPI document = new DocumentAPI();
		document.setAttention("what a fucking day!!");
		document.setCreateTime(System.currentTimeMillis());
		document.setFormat("JSON");
		document.setLogin(false);
		document.setOther("备注：这是什么东西啊！");
		document.setRequestMethod("POST");
		document.setResults("{xxx:xxxx}");
		document.setURL("http://openAPI.gintong.com/document/api");
		List<DocumentRequest> requests = new ArrayList<DocumentRequest>();
		DocumentRequest request = new DocumentRequest();
		request.setName("sourceId");
		request.setRemark("源ID");
		request.setRequired(true);
		request.setType("String");
		requests.add(request);
		document.setRequest(requests);
		documentAPIService.saveDocumentAPI(document);
	}
}
