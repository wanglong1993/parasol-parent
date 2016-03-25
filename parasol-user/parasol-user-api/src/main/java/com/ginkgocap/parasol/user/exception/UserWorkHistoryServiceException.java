package com.ginkgocap.parasol.user.exception;

public class UserWorkHistoryServiceException extends Exception {
	private int errorCode = -1;
	public UserWorkHistoryServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserWorkHistoryServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserWorkHistoryServiceException(String message) {
		super(message);
	}

	
	public UserWorkHistoryServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
