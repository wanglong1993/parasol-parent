package com.ginkgocap.parasol.tags.exception;

/**
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:50:24
 * @Copyright Copyright©2015 www.gintong.com
 */
public class TagServiceException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TagServiceException(int errorCode, String message) {
		super(errorCode, message);
	}

	public TagServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public TagServiceException(String message) {
		super(message);
	}

	public TagServiceException(Throwable cause) {
		super(cause);
	}
}
