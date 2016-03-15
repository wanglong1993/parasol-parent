package com.ginkgocap.parasol.person.exception;

public class PersonBasicServiceException extends Exception {
	private int errorCode = -1;
	public PersonBasicServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public PersonBasicServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonBasicServiceException(String message) {
		super(message);
	}

	
	public PersonBasicServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
