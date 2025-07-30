package org.example.exception;

import org.example.exception.base.ApplicationRuntimeException;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public class ProcessorMethodInvalidFormatException extends ApplicationRuntimeException {

    public ProcessorMethodInvalidFormatException() {
        super();
    }

    public ProcessorMethodInvalidFormatException(String message) {
        super(message);
    }

    public ProcessorMethodInvalidFormatException(Throwable cause) {
        super(cause);
    }

    public ProcessorMethodInvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
