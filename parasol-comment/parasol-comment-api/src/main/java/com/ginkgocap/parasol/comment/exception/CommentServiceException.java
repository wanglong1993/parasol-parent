package com.ginkgocap.parasol.comment.exception;

/**
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:50:24
 * @Copyright Copyright©2015 www.gintong.com
 */
public class CommentServiceException extends Exception {
	private int errorCode = -1;

	public CommentServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public CommentServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommentServiceException(String message) {
		super(message);
	}

	public CommentServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
