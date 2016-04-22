package com.ginkgocap.parasol.mapping.exception;

public abstract class ServiceException extends Exception {
	/**
	 * 
	 */
	private int errorCode = -1;

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
