package com.ginkgocap.parasol.user.exception;

public class UserFriendlyServiceException extends Exception {
	private int errorCode = -1;
	public UserFriendlyServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserFriendlyServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserFriendlyServiceException(String message) {
		super(message);
	}

	
	public UserFriendlyServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
