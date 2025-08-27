package com.mahdy.httpserver.core.model.request;

import com.mahdy.httpserver.core.model.enumeration.HttpMethod;
import com.mahdy.httpserver.core.model.enumeration.HttpVersion;
import lombok.Data;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
public class RequestLine {

    private final HttpMethod httpMethod;
    private final RequestPath requestPath;
    private final HttpVersion httpVersion;
}
