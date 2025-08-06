package org.example.model;

import lombok.Data;
import org.example.model.enumeration.HttpHeader;

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
