package com.mahdy.httpServer.exception;

import com.mahdy.httpServer.exception.base.NotFoundException;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public class RequestPathNotFoundException extends NotFoundException {

    public RequestPathNotFoundException() {
        super();
    }

    public RequestPathNotFoundException(String message) {
        super(message);
    }

    public RequestPathNotFoundException(Throwable cause) {
        super(cause);
    }

    public RequestPathNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
