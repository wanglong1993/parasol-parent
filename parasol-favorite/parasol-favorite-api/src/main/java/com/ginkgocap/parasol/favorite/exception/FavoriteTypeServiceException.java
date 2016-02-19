package com.ginkgocap.parasol.favorite.exception;

/**
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:50:24
 * @Copyright Copyright©2015 www.gintong.com
 */
public class FavoriteTypeServiceException extends Exception {
	private int errorCode = -1;

	public FavoriteTypeServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public FavoriteTypeServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public FavoriteTypeServiceException(String message) {
		super(message);
	}

	public FavoriteTypeServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
