package com.mahdy.httpServer.exception;

import com.mahdy.httpServer.exception.base.NotFoundException;

/**
 * @author Mehdi Kamali
 * @since 04/08/2025
 */
public class ResourceNotFoundException extends NotFoundException {

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
