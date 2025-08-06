package com.mahdy.httpServer.exception;

import com.mahdy.httpServer.exception.base.HandlerException;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public class ExceptionHandlerMethodNotFoundException extends HandlerException {

    public ExceptionHandlerMethodNotFoundException() {
        super();
    }

    public ExceptionHandlerMethodNotFoundException(String message) {
        super(message);
    }

    public ExceptionHandlerMethodNotFoundException(Throwable cause) {
        super(cause);
    }

    public ExceptionHandlerMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
