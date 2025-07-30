package org.example.exception;

import org.example.exception.base.ApplicationRuntimeException;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public class DuplicateProcessorRegisteredException extends ApplicationRuntimeException {

    public DuplicateProcessorRegisteredException() {
        super();
    }

    public DuplicateProcessorRegisteredException(String message) {
        super(message);
    }

    public DuplicateProcessorRegisteredException(Throwable cause) {
        super(cause);
    }

    public DuplicateProcessorRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
