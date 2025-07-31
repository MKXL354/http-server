package org.example.model;

import lombok.Data;
import org.example.model.enumeration.HttpMethod;

/**
 * @author Mehdi Kamali
 * @since 31/07/2025
 */
@Data
public class HttpMethodPath {

    private final HttpMethod httpMethod;
    private final String path;
}
