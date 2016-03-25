package com.ginkgocap.parasol.user.exception;

public class UserConfigServiceException extends Exception {
	private int errorCode = -1;
	public UserConfigServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserConfigServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserConfigServiceException(String message) {
		super(message);
	}

	
	public UserConfigServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
