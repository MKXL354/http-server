package org.example.model;

import lombok.Data;
import org.example.model.enumeration.HttpMethod;

/**
 * @author Mehdi Kamali
 * @since 04/08/2025
 */
@Data
public class VariableHttpMethodPath {

    private final HttpMethod httpMethod;
    private final String[] pathSegments;
}
