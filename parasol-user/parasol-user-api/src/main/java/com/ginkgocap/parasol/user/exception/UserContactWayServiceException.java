package com.ginkgocap.parasol.user.exception;

public class UserContactWayServiceException extends Exception {
	private int errorCode = -1;
	public UserContactWayServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserContactWayServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserContactWayServiceException(String message) {
		super(message);
	}

	
	public UserContactWayServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
