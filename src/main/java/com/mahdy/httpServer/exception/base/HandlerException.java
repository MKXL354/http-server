package com.mahdy.httpServer.exception.base;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public class HandlerException extends ApplicationRuntimeException {

    public HandlerException() {
        super();
    }

    public HandlerException(String message) {
        super(message);
    }

    public HandlerException(Throwable cause) {
        super(cause);
    }

    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
