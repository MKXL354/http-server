package org.example.model;

import lombok.Data;

import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
public class HttpHeaders {

    private final Map<HttpHeader, String> headers;

    public String getHeaderValue(HttpHeader header) {
        return headers.get(header);
    }
}
