package org.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
@AllArgsConstructor
public class RequestPath {

    private String pathString;
    private final Map<String, String> queryParameters = new HashMap<>();
    private final Map<String, String> pathVariables = new HashMap<>();
}
