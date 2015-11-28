package com.ginkgocap.parasol.user.exception;


public class UserOrgPerCusRelServiceException extends Exception {
	private int errorCode = -1;
	public UserOrgPerCusRelServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserOrgPerCusRelServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserOrgPerCusRelServiceException(String message) {
		super(message);
	}

	
	public UserOrgPerCusRelServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
