package com.mahdy.httpserver.core.exception.base;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class ApplicationRuntimeException extends RuntimeException {

    public ApplicationRuntimeException() {
        super();
    }

    public ApplicationRuntimeException(String message) {
        super(message);
    }

    public ApplicationRuntimeException(Throwable cause) {
        super(cause);
    }

    public ApplicationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
