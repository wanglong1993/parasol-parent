package com.ginkgocap.parasol.user.exception;

public class UserEducationHistoryServiceException extends Exception {
	private int errorCode = -1;
	public UserEducationHistoryServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserEducationHistoryServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserEducationHistoryServiceException(String message) {
		super(message);
	}

	
	public UserEducationHistoryServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
