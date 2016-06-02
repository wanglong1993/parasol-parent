package com.ginkgocap.parasol.comment.exception;

public class CommObjServiceException extends Exception {
	private int errorCode = -1;

	public CommObjServiceException(Throwable cause) {
		super(cause);
	}


	public CommObjServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommObjServiceException(String message) {
		super(message);
	}

	public CommObjServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
