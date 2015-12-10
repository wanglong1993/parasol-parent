package com.ginkgocap.parasol.email.service;


/**
 * 操作邮件接口定义
 */
public interface EmailService {

    /**
     * send email immediately, this method will block because the method is synchronized.
     * @param mailTo
     * @param mailFrom
     * @param mailTitle  the mail title
     * @param mailContext the mail context
     * @param attachment the mail attachment 
     */
    public boolean sendEmailSync(String mailTo, String mailFrom, String mailTitle, String mailContext, String attachment);

    /**
     * send email immediately, this method non-block because the method is asynchronized, we could not get the status.
     * @param mailTo
     * @param mailFrom
     * @param mailTitle  the mail title
     * @param mailContext the mail context
     * @param attachment the mail attachment 
     */
    public void sendEmailAsync(String mailTo, String mailFrom, String mailTitle, String mailContext, String attachment);
}
