package org.example.exception;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class MalformedHttpRequestException extends ApplicationRuntimeException {

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
//TODO: meaningful messages read from config encapsulated in exceptions?
