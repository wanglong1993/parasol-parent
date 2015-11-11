package com.ginkgocap.ywxt.metadata.service2.exception;

public class BaseServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1032552649650406765L;

	public BaseServiceException(String message) {
		super(message);
	}

	public BaseServiceException(Throwable cause) {
		super(cause);
	}
	
	
}
