package com.mahdy.httpServer.model;

import com.mahdy.httpServer.model.enumeration.HttpMethod;
import lombok.Getter;

/**
 * @author Mehdi Kamali
 * @since 04/08/2025
 */
@Getter
public class VariableHttpMethodPath extends HttpMethodPath {

    private final String[] pathSegments;

    public VariableHttpMethodPath(HttpMethod httpMethod, String path, String[] pathSegments) {
        super(httpMethod, path);
        this.pathSegments = pathSegments;
    }
}
