package org.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.enumeration.HttpMethod;
import org.example.model.enumeration.HttpVersion;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
@AllArgsConstructor
public class RequestLine {

    private HttpMethod httpMethod;
    private RequestPath requestPath;
    private HttpVersion httpVersion;
}
