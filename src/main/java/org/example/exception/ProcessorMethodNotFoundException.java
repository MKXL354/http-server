package org.example.exception;

import org.example.exception.base.ApplicationException;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public class ProcessorMethodNotFoundException extends ApplicationException {

    public ProcessorMethodNotFoundException() {
        super();
    }

    public ProcessorMethodNotFoundException(String message) {
        super(message);
    }

    public ProcessorMethodNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProcessorMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
