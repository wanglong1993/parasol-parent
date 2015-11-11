package com.ginkgocap.parasol.sms.receive;

/**
 * 
* <p>Title: DealReceivedSmsTask.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-11 
* @version 1.0
 */
public interface DealReceivedSmsTask {
    /**实现处理方法
     * @param mobile 手机号码
     * @param msg 短信内容
     * @param source 短信来源
     * @return 是否处理过
     */
    boolean deal(String mobile, String msg, String source);
    
    /**接口实现类的标志名字
     * @return
     */
    String getName(); 
}
