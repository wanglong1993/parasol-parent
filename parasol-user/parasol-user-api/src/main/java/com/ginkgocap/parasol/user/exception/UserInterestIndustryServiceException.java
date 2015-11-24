package com.ginkgocap.parasol.user.exception;

public class UserInterestIndustryServiceException extends Exception {
	private int errorCode = -1; 
	public UserInterestIndustryServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public UserInterestIndustryServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserInterestIndustryServiceException(String message) {
		super(message);
	}

	
	public UserInterestIndustryServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

}
