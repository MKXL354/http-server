package org.example.model;

import lombok.Getter;
import org.example.model.enumeration.HttpMethod;

/**
 * @author Mehdi Kamali
 * @since 31/07/2025
 */
@Getter
public class HttpMethodPath {

    private final HttpMethod httpMethod;
    private final String path;

    public HttpMethodPath(HttpMethod httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }
}
