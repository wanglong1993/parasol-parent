package com.ginkgocap.parasol.user.exception;

public class UserBasicServiceException extends Exception {
	private int errorCode = -1; 
	public UserBasicServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserBasicServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserBasicServiceException(String message) {
		super(message);
	}

	
	public UserBasicServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

}
