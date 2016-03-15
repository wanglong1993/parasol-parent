package com.ginkgocap.parasol.person.exception;

public class PersonContactWayServiceException extends Exception {
	private int errorCode = -1;
	public PersonContactWayServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public PersonContactWayServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonContactWayServiceException(String message) {
		super(message);
	}

	
	public PersonContactWayServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
