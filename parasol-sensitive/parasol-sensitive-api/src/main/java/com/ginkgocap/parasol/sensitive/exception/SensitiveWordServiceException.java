package com.ginkgocap.parasol.sensitive.exception;

/**
 * 
* @Title: SensitiveWordServiceException.java
* @Package com.ginkgocap.parasol.sensitive.exception
* @Description: TODO(敏感词服务异常类)
* @author fuliwen@gintong.com  
* @date 2015年12月8日 下午4:03:19
* @version V1.0
 */

public class SensitiveWordServiceException extends Exception {
	private int errorCode = -1;

	public SensitiveWordServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public SensitiveWordServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public SensitiveWordServiceException(String message) {
		super(message);
	}

	public SensitiveWordServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
