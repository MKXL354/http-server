package com.mahdy.httpServer.exception.base;

/**
 * @author Mehdi Kamali
 * @since 31/07/2025
 */
public class RequestException extends ApplicationException {

    public RequestException() {
        super();
    }

    public RequestException(String message) {
        super(message);
    }

    public RequestException(Throwable cause) {
        super(cause);
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
