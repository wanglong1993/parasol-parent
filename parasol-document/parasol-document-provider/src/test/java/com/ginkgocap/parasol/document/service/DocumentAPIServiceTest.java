package com.ginkgocap.parasol.document.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
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
		JSONObject json = new JSONObject();
//		 "created_at": ,
//         "id": 11488058246,
//         "": "求关注。"，
//         "source": "<a href="http://weibo.com" rel="nofollow">新浪微博</a>",
//         "favorited": false,
//         "truncated": false,
//         "in_reply_to_status_id": "",
//         "in_reply_to_user_id": "",
//         "in_reply_to_screen_name": "",
//         "geo": null,
//         "mid": "5612814510546515491",
//         "reposts_count": 8,
//         "comments_count": 9,
//         "annotations": [],
		json.put("created_at", "Tue May 31 17:46:55 +0800 2011");
		json.put("id", 11488058246l);
		json.put("text", "求关注。");
		document.setResults(json);
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
	
	public void getDocumentAPIById() {
		long id = 2;
		DocumentAPI document = documentAPIService.getDocumentAPIByID(id);
		JSONObject object = JSONObject.;
		System.out.println(object);
	}
}
