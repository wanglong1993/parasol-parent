package com.ginkgocap.parasol.favorite.exception;

/**
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:50:24
 * @Copyright Copyright©2015 www.gintong.com
 */
public class FavoriteSourceServiceException extends Exception {
	private int errorCode = -1;

	public FavoriteSourceServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821388402674737444L;

	public FavoriteSourceServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public FavoriteSourceServiceException(String message) {
		super(message);
	}

	public FavoriteSourceServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
