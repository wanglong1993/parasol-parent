package com.ginkgocap.parasol.person.exception;

public class PersonEducationHistoryServiceException extends Exception {
	private int errorCode = -1;
	public PersonEducationHistoryServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public PersonEducationHistoryServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonEducationHistoryServiceException(String message) {
		super(message);
	}

	
	public PersonEducationHistoryServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
