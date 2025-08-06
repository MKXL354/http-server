package com.mahdy.httpServer.model.request;

import com.mahdy.httpServer.model.enumeration.HttpMethod;
import com.mahdy.httpServer.model.enumeration.HttpVersion;
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
