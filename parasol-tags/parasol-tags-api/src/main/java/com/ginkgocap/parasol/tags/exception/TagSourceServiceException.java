package com.ginkgocap.parasol.tags.exception;

/**
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:50:24
 * @Copyright Copyright©2015 www.gintong.com
 */
public class TagSourceServiceException extends ServiceException {
	private int errorCode = -1;

	public TagSourceServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public TagSourceServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public TagSourceServiceException(String message) {
		super(message);
	}

	public TagSourceServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
