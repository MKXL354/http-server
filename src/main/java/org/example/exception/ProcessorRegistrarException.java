package org.example.exception;

import org.example.exception.base.ApplicationRuntimeException;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public class ProcessorRegistrarException extends ApplicationRuntimeException {

    public ProcessorRegistrarException() {
        super();
    }

    public ProcessorRegistrarException(String message) {
        super(message);
    }

    public ProcessorRegistrarException(Throwable cause) {
        super(cause);
    }

    public ProcessorRegistrarException(String message, Throwable cause) {
        super(message, cause);
    }
}
