package com.ginkgocap.parasol.knowledge.model.common;

import java.io.Serializable;

/**
 * <p>DESCRIPTION:  返回给客户端的标识对象
 * <p>CALLED BY:	钱明金
 * <p>CREATE DATE: 2015/12/9
 *
 * @version 1.0
 * @since java 1.7.0
 */
public class Notification implements Serializable{
    //返回信息编码
    private String notifCode;
    //返回信息
    private String notifInfo;

    public Notification() {
    }

    public Notification(CommonResultCode commonResultCode) {
        this.notifCode = commonResultCode.getCode();
        this.notifInfo = commonResultCode.getMsg();
    }

    public Notification(String notifCode, String notifInfo) {
        this.notifCode = notifCode;
        this.notifInfo = notifInfo;
    }

    public String getNotifCode() {
        return notifCode;
    }

    public void setNotifCode(String notifCode) {
        this.notifCode = notifCode;
    }

    public String getNotifInfo() {
        return notifInfo;
    }

    public void setNotifInfo(String notifInfo) {
        this.notifInfo = notifInfo;
    }
}
