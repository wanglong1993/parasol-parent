package com.ginkgocap.parasol.sms.service;

/**
 * 
* <p>Title: ShortMessageService.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-11 
* @version 1.0
 */
public interface ShortMessageService {
    

	/**
	 * 将发送短信发到消息队列中
	 * @param mobile
	 * @param msg
	 * @param uid
	 * @param type
	 * @return 
	 */
	int sendMessage(String mobile,String msg, long uid, int type);
    
}
