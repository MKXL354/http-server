package org.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.example.model.HttpBody;
import org.example.model.HttpContext;
import org.example.model.HttpHeaders;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@AllArgsConstructor
@Getter
@ToString
public class HttpRequest {

    private RequestLine requestLine;
    private HttpHeaders headers;
    private HttpBody body;
    private HttpContext httpContext;
}
