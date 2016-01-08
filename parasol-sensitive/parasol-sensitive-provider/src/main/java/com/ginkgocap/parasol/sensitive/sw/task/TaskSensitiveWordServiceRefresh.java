package com.ginkgocap.parasol.sensitive.sw.task;

import javax.annotation.Resource;

import com.ginkgocap.parasol.sensitive.service.SensitiveWordService;

/**
 * 
* <p>Title: AbstractProcessor.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-26 
* @version 1.0
 */
public class TaskSensitiveWordServiceRefresh {
	
	@Resource
	private SensitiveWordService sensitiveWordService;
	public void freshSensitiveWord(){  
		sensitiveWordService.updateWord();
    } 
}
