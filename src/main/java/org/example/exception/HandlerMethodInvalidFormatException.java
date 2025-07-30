package org.example.exception;

import org.example.exception.base.HandlerException;

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
