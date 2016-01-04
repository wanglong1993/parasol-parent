package com.ginkgocap.parasol.oauth2.exception;

public class OauthClientDetailsServiceException extends Exception {
	private int errorCode = -1;
	public OauthClientDetailsServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public OauthClientDetailsServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public OauthClientDetailsServiceException(String message) {
		super(message);
	}

	
	public OauthClientDetailsServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
