package com.mahdy.httpserver.core.model.request;

import com.mahdy.httpserver.core.model.HttpBody;
import com.mahdy.httpserver.core.model.HttpContext;
import com.mahdy.httpserver.core.model.HttpHeaders;
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
