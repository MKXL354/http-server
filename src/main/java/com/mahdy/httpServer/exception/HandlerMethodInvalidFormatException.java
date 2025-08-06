package com.mahdy.httpServer.exception;

import com.mahdy.httpServer.exception.base.HandlerException;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public class HandlerMethodInvalidFormatException extends HandlerException {

    public HandlerMethodInvalidFormatException() {
        super();
    }

    public HandlerMethodInvalidFormatException(String message) {
        super(message);
    }

    public HandlerMethodInvalidFormatException(Throwable cause) {
        super(cause);
    }

    public HandlerMethodInvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
