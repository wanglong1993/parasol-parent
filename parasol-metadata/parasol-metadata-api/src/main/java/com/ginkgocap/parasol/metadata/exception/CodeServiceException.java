package com.ginkgocap.parasol.metadata.exception;

/**
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:50:24
 * @Copyright Copyright©2015 www.gintong.com
 */
public class CodeServiceException extends Exception {
	private int errorCode = -1;
	public CodeServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public CodeServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CodeServiceException(String message) {
		super(message);
	}

	
	public CodeServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

}
