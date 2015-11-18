package com.ginkgocap.parasol.message.service;

/**
 * 
* <p>Title: MessageRelation.java<／p> 
* <p>Description: message relation <／p> 

* @author fuliwen 
* @date 2015-11-18 
* @version 1.0
 */
public interface MessageRelationService {
	
	public int insertMessageRelation();
	
	public int insertBatchMessageRelation();
	
	public int delMessageRelation();
	
	public int delBatchMessageRelation();
	
}
