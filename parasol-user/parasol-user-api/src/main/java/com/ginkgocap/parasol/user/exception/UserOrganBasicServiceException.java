package com.ginkgocap.parasol.user.exception;

public class UserOrganBasicServiceException extends Exception {
	private int errorCode = -1;
	public UserOrganBasicServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserOrganBasicServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserOrganBasicServiceException(String message) {
		super(message);
	}

	
	public UserOrganBasicServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
