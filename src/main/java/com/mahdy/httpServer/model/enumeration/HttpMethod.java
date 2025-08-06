package com.mahdy.httpServer.model.enumeration;

import java.util.Arrays;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public enum HttpMethod {

    GET,
    POST,
    PUT,
    PATCH,
    DELETE;

    public static HttpMethod getByValue(String value) {
        return Arrays.stream(HttpMethod.values()).filter(m -> m.toString().equals(value)).findFirst().orElse(null);
    }
}
