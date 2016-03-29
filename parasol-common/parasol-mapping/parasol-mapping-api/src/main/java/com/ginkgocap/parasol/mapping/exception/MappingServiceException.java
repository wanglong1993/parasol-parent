package com.ginkgocap.parasol.mapping.exception;

/**
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:50:24
 * @Copyright Copyright©2015 www.gintong.com
 */
public class MappingServiceException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MappingServiceException(int errorCode, String message) {
		super(errorCode, message);
	}

	public MappingServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MappingServiceException(String message) {
		super(message);
	}

	public MappingServiceException(Throwable cause) {
		super(cause);
	}
}
