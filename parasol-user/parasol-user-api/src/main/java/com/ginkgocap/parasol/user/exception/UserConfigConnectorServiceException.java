package com.ginkgocap.parasol.user.exception;

/**
 * Created by xutlong on 2016/5/31.
 */
public class UserConfigConnectorServiceException extends Exception{
    private int errorCode = -1;
    public UserConfigConnectorServiceException(Throwable cause) {
        super(cause);
    }

    /**
     *
     */
    private static final long serialVersionUID = -7821388402674737444L;

    public UserConfigConnectorServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserConfigConnectorServiceException(String message) {
        super(message);
    }


    public UserConfigConnectorServiceException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
