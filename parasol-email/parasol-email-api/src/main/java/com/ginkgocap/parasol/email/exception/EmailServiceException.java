package com.ginkgocap.parasol.email.exception;

public class EmailServiceException extends Exception {
	private int errorCode = -1;
	public EmailServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public EmailServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailServiceException(String message) {
		super(message);
	}

	
	public EmailServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
