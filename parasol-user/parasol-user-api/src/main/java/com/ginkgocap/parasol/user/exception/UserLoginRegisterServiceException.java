package com.ginkgocap.parasol.user.exception;

public class UserLoginRegisterServiceException extends Exception {
	private int errorCode = -1; 
	public UserLoginRegisterServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserLoginRegisterServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserLoginRegisterServiceException(String message) {
		super(message);
	}

	
	public UserLoginRegisterServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

}
