package org.example.exception.base;

/**
 * @author Mehdi Kamali
 * @since 04/08/2025
 */
public class NotFoundException extends RequestException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
