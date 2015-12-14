package com.ginkgocap.parasol.message.exception;

/**
 * 
* @Title: MessageEntityServiceException.java
* @Package com.ginkgocap.parasol.message.exception
* @Description: TODO(消息提醒异常类)
* @author fuliwen fuliwen@gintong.com  
* @date 2015年12月8日 下午4:01:48
* @version V1.0
 */
public class MessageEntityServiceException extends Exception {
	private int errorCode = -1;

	public MessageEntityServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public MessageEntityServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageEntityServiceException(String message) {
		super(message);
	}

	public MessageEntityServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
