package com.ginkgocap.parasol.file.exception;

/**
 * 
* @Title: FileIndexServiceException.java
* @Package com.ginkgocap.parasol.file.exception
* @Description: TODO(文件索引异常处理)
* @author fuliwen@gintong.com  
* @date 2015年12月15日 下午2:12:31
* @version V1.0
 */
public class FileIndexServiceException extends Exception {
	private int errorCode = -1;

	public FileIndexServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public FileIndexServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileIndexServiceException(String message) {
		super(message);
	}

	public FileIndexServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
