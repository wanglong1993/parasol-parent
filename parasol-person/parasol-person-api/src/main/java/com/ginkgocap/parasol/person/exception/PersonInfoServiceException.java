package com.ginkgocap.parasol.person.exception;

public class PersonInfoServiceException extends Exception {
	private int errorCode = -1;
	public PersonInfoServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public PersonInfoServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonInfoServiceException(String message) {
		super(message);
	}

	
	public PersonInfoServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
