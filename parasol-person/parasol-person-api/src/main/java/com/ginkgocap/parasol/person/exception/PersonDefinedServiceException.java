package com.ginkgocap.parasol.person.exception;

public class PersonDefinedServiceException extends Exception {
	private int errorCode = -1;
	public PersonDefinedServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public PersonDefinedServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonDefinedServiceException(String message) {
		super(message);
	}

	
	public PersonDefinedServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
