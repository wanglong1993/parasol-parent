package com.ginkgocap.parasol.email.service;

import java.util.Map;

import com.ginkgocap.parasol.email.exception.EmailServiceException;


/**
 * 操作邮件接口定义
 */
public interface EmailService {

    /**
     * 发送邮件
     * @param mailTo
     * @param mailFrom
     * @param mailTitle
     * @param attachment
     * @param map
     * @param template
     */
    public boolean sendEmailSync(String mailTo, String mailFrom, String mailTitle, String attachment,Map<String, Object> map,String template) throws EmailServiceException;
}
