package com.mahdy.httpserver.core.exception;

import com.mahdy.httpserver.core.exception.base.ApplicationException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class MalformedHttpRequestException extends ApplicationException {

    public MalformedHttpRequestException() {
        super();
    }

    public MalformedHttpRequestException(String message) {
        super(message);
    }

    public MalformedHttpRequestException(Throwable cause) {
        super(cause);
    }

    public MalformedHttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
