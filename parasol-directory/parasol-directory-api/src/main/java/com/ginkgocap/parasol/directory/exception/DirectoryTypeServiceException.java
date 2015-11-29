package com.ginkgocap.parasol.directory.exception;

/**
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:50:24
 * @Copyright Copyright©2015 www.gintong.com
 */
public class DirectoryTypeServiceException extends Exception {
	private int errorCode = -1;

	public DirectoryTypeServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public DirectoryTypeServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DirectoryTypeServiceException(String message) {
		super(message);
	}

	public DirectoryTypeServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
