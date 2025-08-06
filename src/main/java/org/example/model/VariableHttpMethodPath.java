package org.example.model;

import lombok.Getter;
import org.example.model.enumeration.HttpMethod;

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
