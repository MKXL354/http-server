package com.mahdy.httpServer.model.request;

import com.mahdy.httpServer.model.HttpBody;
import com.mahdy.httpServer.model.HttpContext;
import com.mahdy.httpServer.model.HttpHeaders;
import lombok.Data;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpHeaders headers;
    private final HttpBody body;
    private final HttpContext httpContext;
}
