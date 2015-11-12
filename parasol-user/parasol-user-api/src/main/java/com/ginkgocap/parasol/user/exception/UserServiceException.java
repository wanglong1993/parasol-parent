package com.ginkgocap.parasol.user.exception;

public class UserServiceException extends Exception {
	private int errorCode = -1;
	public UserServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserServiceException(String message) {
		super(message);
	}

	
	public UserServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

}
