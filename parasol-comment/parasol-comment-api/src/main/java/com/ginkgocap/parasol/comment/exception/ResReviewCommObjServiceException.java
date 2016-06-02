package com.ginkgocap.parasol.comment.exception;

public class ResReviewCommObjServiceException extends Exception {
	private int errorCode = -1;

	public ResReviewCommObjServiceException(Throwable cause) {
		super(cause);
	}


	public ResReviewCommObjServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResReviewCommObjServiceException(String message) {
		super(message);
	}

	public ResReviewCommObjServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
