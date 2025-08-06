package com.mahdy.httpServer.model;

import com.mahdy.httpServer.model.enumeration.HttpHeader;
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
