package org.example.exception;

import org.example.exception.base.HandlerException;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
public class DuplicateMiddlewareOrderRegisteredException extends HandlerException {

    public DuplicateMiddlewareOrderRegisteredException() {
        super();
    }

    public DuplicateMiddlewareOrderRegisteredException(String message) {
        super(message);
    }

    public DuplicateMiddlewareOrderRegisteredException(Throwable cause) {
        super(cause);
    }

    public DuplicateMiddlewareOrderRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
