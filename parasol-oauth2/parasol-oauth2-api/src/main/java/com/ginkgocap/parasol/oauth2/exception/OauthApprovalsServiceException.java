package com.ginkgocap.parasol.oauth2.exception;

public class OauthApprovalsServiceException extends Exception {
	private int errorCode = -1;
	public OauthApprovalsServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public OauthApprovalsServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public OauthApprovalsServiceException(String message) {
		super(message);
	}

	
	public OauthApprovalsServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
