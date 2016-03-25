package com.ginkgocap.parasol.user.exception;

public class UserInfoServiceException extends Exception {
	private int errorCode = -1;
	public UserInfoServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserInfoServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserInfoServiceException(String message) {
		super(message);
	}

	
	public UserInfoServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
