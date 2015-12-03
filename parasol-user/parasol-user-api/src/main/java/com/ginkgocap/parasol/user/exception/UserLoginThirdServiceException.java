package com.ginkgocap.parasol.user.exception;

public class UserLoginThirdServiceException extends Exception {
	private int errorCode = -1;
	public UserLoginThirdServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserLoginThirdServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserLoginThirdServiceException(String message) {
		super(message);
	}

	
	public UserLoginThirdServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
