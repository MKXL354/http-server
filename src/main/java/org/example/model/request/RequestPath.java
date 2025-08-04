package org.example.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

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
    @Setter(AccessLevel.NONE)
    private final Map<String, String> queryParameters = new HashMap<>();

    public void addQueryParameter(String key, String value) {
        queryParameters.put(key, value);
    }

    public String getQueryParameter(String key) {
        return queryParameters.get(key);
    }
}
