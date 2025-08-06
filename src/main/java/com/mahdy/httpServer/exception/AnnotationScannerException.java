package com.mahdy.httpServer.exception;

import com.mahdy.httpServer.exception.base.ApplicationRuntimeException;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public class AnnotationScannerException extends ApplicationRuntimeException {

    public AnnotationScannerException() {
        super();
    }

    public AnnotationScannerException(String message) {
        super(message);
    }

    public AnnotationScannerException(Throwable cause) {
        super(cause);
    }

    public AnnotationScannerException(String message, Throwable cause) {
        super(message, cause);
    }
}
