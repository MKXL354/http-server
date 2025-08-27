package com.mahdy.httpserver.core.model;

import com.mahdy.httpserver.core.model.enumeration.HttpHeader;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
public class HttpHeaders {

    private final Map<HttpHeader, String> headerMap = new HashMap<>();
}
