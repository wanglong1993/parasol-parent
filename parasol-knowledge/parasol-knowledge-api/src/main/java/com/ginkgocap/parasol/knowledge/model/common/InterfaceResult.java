package com.ginkgocap.parasol.knowledge.model.common;

import java.io.Serializable;

/**
 * <p>DESCRIPTION:  给客户端返回数据的封装对象
 * <p>CALLED BY:	钱明金
 * <p>CREATE DATE: 2015/12/9
 *
 * @version 1.0
 * @since java 1.7.0
 */
public  class InterfaceResult<T> implements Serializable{
    private Notification notification;
    private T responseData;

    public InterfaceResult() {
    }

    public InterfaceResult(CommonResultCode commonResultCode) {
        this.notification = new Notification(commonResultCode);
    }

    public InterfaceResult(Notification notification) {
        this.notification = notification;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public static InterfaceResult getInterfaceResultInstance(CommonResultCode commonResultCode) {
        InterfaceResult interfaceResult = new InterfaceResult(commonResultCode);
        return interfaceResult;
    }

    public static InterfaceResult getInterfaceResultInstance(String notifCode, String notifInfo) {
        Notification notification = new Notification(notifCode, notifInfo);
        InterfaceResult interfaceResult = new InterfaceResult(notification);
        return interfaceResult;
    }

    /**
     * 获取成功的interface对象
     *
     * @return
     */
    public static <T> InterfaceResult<T> getSuccessInterfaceResultInstance(T responseData) {
        InterfaceResult interfaceResult = new InterfaceResult(CommonResultCode.SUCCESS);
        interfaceResult.setResponseData(responseData);
        return interfaceResult;
    }

    /**
     * 获取带有异常message的interface对象
     * @param commonResultCode
     * @param e
     * @param <T>
     * @return
     */
    public static <T> InterfaceResult<T> getInterfaceResultInstanceWithException(CommonResultCode commonResultCode, Exception e) {
        String msg = commonResultCode.getMsg() + e.getMessage();
        InterfaceResult interfaceResult = getInterfaceResultInstance(commonResultCode.getCode(), msg);
        return interfaceResult;
    }
}
