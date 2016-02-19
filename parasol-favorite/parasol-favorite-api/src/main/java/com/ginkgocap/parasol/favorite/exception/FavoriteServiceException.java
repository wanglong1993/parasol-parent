package com.ginkgocap.parasol.favorite.exception;

/**
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:50:24
 * @Copyright Copyright©2015 www.gintong.com
 */
public class FavoriteServiceException extends Exception {
	private int errorCode = -1;

	public FavoriteServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public FavoriteServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public FavoriteServiceException(String message) {
		super(message);
	}

	public FavoriteServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
