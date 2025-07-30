package org.example.exception;

import org.example.exception.base.HandlerException;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public class DuplicateHandlerMethodRegisteredException extends HandlerException {

    public DuplicateHandlerMethodRegisteredException() {
        super();
    }

    public DuplicateHandlerMethodRegisteredException(String message) {
        super(message);
    }

    public DuplicateHandlerMethodRegisteredException(Throwable cause) {
        super(cause);
    }

    public DuplicateHandlerMethodRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
