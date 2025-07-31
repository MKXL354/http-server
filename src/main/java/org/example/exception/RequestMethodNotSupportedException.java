package org.example.exception;

import org.example.exception.base.RequestException;

/**
 * @author Mehdi Kamali
 * @since 31/07/2025
 */
public class RequestMethodNotSupportedException extends RequestException {

    public RequestMethodNotSupportedException() {
        super();
    }

    public RequestMethodNotSupportedException(String message) {
        super(message);
    }

    public RequestMethodNotSupportedException(Throwable cause) {
        super(cause);
    }

    public RequestMethodNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
