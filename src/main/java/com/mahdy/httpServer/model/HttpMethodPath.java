package com.mahdy.httpServer.model;

import com.mahdy.httpServer.model.enumeration.HttpMethod;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Mehdi Kamali
 * @since 31/07/2025
 */
@Getter
@EqualsAndHashCode
public class HttpMethodPath {

    protected final HttpMethod httpMethod;
    protected final String path;

    public HttpMethodPath(HttpMethod httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }
}
