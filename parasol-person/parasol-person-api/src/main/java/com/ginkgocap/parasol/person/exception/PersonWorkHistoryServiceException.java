package com.ginkgocap.parasol.person.exception;

public class PersonWorkHistoryServiceException extends Exception {
	private int errorCode = -1;
	public PersonWorkHistoryServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public PersonWorkHistoryServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonWorkHistoryServiceException(String message) {
		super(message);
	}

	
	public PersonWorkHistoryServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
