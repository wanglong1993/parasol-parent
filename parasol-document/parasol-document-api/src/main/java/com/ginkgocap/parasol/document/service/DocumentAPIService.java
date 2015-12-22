package com.ginkgocap.parasol.document.service;

import com.ginkgocap.parasol.document.model.DocumentAPI;

/**
 * 
* @Title: DocumentAPIService.java
* @Package com.ginkgocap.parasol.document.service
* @Description: TODO(文档APIService)
* @author fuliwen@gintong.com  
* @date 2015年12月22日 上午11:22:58
* @version V1.0
 */
public interface DocumentAPIService {
	
	DocumentAPI	saveDocumentAPI(DocumentAPI document);
	
	DocumentAPI getDocumentAPIByID(long id);
	
	DocumentAPI updateDocumentAPI(DocumentAPI document);
}
