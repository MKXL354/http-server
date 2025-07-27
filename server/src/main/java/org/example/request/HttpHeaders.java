package org.example.request;

import lombok.Data;

import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
public class HttpHeaders {

    private final Map<HttpHeader, String> headers;
}
