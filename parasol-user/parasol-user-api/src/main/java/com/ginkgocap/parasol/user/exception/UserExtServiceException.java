package com.ginkgocap.parasol.user.exception;

public class UserExtServiceException extends Exception {
	private int errorCode = -1;
	public UserExtServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserExtServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserExtServiceException(String message) {
		super(message);
	}

	
	public UserExtServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
