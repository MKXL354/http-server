package org.example.model.request;

import lombok.Data;
import org.example.model.enumeration.HttpMethod;
import org.example.model.enumeration.HttpVersion;

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
