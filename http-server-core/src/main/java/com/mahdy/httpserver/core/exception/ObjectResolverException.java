package com.mahdy.httpserver.core.exception;

import com.mahdy.httpserver.core.exception.base.ApplicationRuntimeException;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
public class ObjectResolverException extends ApplicationRuntimeException {

    public ObjectResolverException() {
        super();
    }

    public ObjectResolverException(String message) {
        super(message);
    }

    public ObjectResolverException(Throwable cause) {
        super(cause);
    }

    public ObjectResolverException(String message, Throwable cause) {
        super(message, cause);
    }
}
