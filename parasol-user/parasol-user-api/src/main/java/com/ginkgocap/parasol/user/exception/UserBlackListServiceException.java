package com.ginkgocap.parasol.user.exception;

/**
 * Created by xutlong on 2016/5/24.
 */
public class UserBlackListServiceException extends Exception {

    private int errorCode = -1;

    public UserBlackListServiceException(Throwable cause) {
        super(cause);
    }

    public UserBlackListServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserBlackListServiceException(String message) {
        super(message);
    }

    public UserBlackListServiceException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
