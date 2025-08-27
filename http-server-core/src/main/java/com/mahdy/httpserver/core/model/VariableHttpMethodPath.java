package com.mahdy.httpserver.core.model;

import com.mahdy.httpserver.core.model.enumeration.HttpMethod;
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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
