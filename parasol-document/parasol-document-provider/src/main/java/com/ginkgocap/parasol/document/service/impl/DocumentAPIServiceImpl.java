package com.ginkgocap.parasol.document.service.impl;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.DocumentCommonService;
import com.ginkgocap.parasol.document.model.DocumentAPI;
import com.ginkgocap.parasol.document.service.DocumentAPIService;

/**
 * 
* <p>Title: FileIndexServiceImpl.java<／p> 
* <p>Description: 文件上传service<／p> 

* @author fuliwen 
* @date 2015-11-30 
* @version 1.0
 */
@Service("documentAPIService")
public class DocumentAPIServiceImpl implements DocumentAPIService {

    @Resource
    private MongoTemplate mongoTemplate;
    
    @Resource
    private DocumentCommonService documentCommonService;
	
	@Override
	public DocumentAPI saveDocumentAPI(DocumentAPI document) {
		if(document.getId() == 0) {
			document.setId(documentCommonService.getDocumentIncreaseId());
		}
		mongoTemplate.save(document);
		return document;
	}

	@Override
	public DocumentAPI getDocumentAPIByID(long id) {
		DocumentAPI document = mongoTemplate.findById(id, DocumentAPI.class);
		return document;
	}

	@Override
	public DocumentAPI updateDocumentAPI(DocumentAPI document) {
		mongoTemplate.insert(document);
		return document;
	}
	
}
