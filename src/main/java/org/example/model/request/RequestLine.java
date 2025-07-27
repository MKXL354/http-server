package org.example.model.request;

import lombok.Data;
import org.example.model.HttpVersion;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
public class RequestLine {

    private final HttpMethod httpMethod;
    private final RequestPath path;
    private final HttpVersion httpVersion;
}
