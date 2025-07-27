package org.example.model.request;

import lombok.Data;
import org.example.model.HttpBody;
import org.example.model.HttpHeaders;

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
