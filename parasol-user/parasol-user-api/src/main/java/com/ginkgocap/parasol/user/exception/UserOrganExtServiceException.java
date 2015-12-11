package com.ginkgocap.parasol.user.exception;

public class UserOrganExtServiceException extends Exception {
	private int errorCode = -1;
	public UserOrganExtServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserOrganExtServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserOrganExtServiceException(String message) {
		super(message);
	}

	
	public UserOrganExtServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
