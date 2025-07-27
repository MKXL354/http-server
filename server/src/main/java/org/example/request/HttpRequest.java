package org.example.request;

import lombok.Data;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpHeaders headers;
    private final HttpBody httpBody;
}
