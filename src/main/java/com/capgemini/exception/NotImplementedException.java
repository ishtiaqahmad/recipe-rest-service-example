package com.capgemini.exception;

/**
 * Is thrown when a certain (future) feature is not yet implemented.
 * Thus helping to avoid future failures, for example when switching on an enum that
 * has more values than implemented presently.
 */
public class NotImplementedException extends Exception {

    private static final long serialVersionUID = 1L;

    public NotImplementedException() {
        super();
    }

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(Throwable cause) {
        super(cause);
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImplementedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
