package com.ginkgocap.parasol.user.exception;

public class UserDefinedServiceException extends Exception {
	private int errorCode = -1;
	public UserDefinedServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserDefinedServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserDefinedServiceException(String message) {
		super(message);
	}

	
	public UserDefinedServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
