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
    
    /**发送短信，自动在末尾附加【金桐网】,
     * @param mobile 手机号 多个号码使用逗号隔开
     * @param msg 短信内容 长度不限制
     * @return 1 发送成功<br>0 发送失败<br>-1 下发号码不正确<br>-2 短信内容为空
     * @throws CacheException 
     */
    int sendMessage(String mobile,String msg, long uid, int type);
    /**发送短信，自动在末尾附加【金桐网】
     * @param mobile 手机号 多个号码使用逗号隔开
     * @param msg 短信内容 长度不限制
     * @return 1 发送成功<br>0 发送失败<br>-1 下发号码不正确<br>-2 短信内容为空
     */
    int sendMiniMessage(String mobile,String msg);
    
}
