package com.hualxx.crowd.exception;

/**
 * @author hual
 * @create 2020-11-13 6:08
 */
public class LoginAcctAlreadyUseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LoginAcctAlreadyUseException() {
    }

    public LoginAcctAlreadyUseException(String message) {
        super(message);
    }

    public LoginAcctAlreadyUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyUseException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
