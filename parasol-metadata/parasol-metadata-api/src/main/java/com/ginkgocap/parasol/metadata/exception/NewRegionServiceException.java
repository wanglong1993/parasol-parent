package com.ginkgocap.parasol.metadata.exception;

/**
 * Created by xutlong on 2017/10/30.
 */
public class NewRegionServiceException extends Exception {

    private int errorCode = -1;

    public NewRegionServiceException(Throwable cause) {
        super(cause);
    }

    public NewRegionServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewRegionServiceException(String message) {
        super(message);
    }


    public NewRegionServiceException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
