package org.example.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
@Data
public class HttpContext {

    private String clientIp;
    private boolean isConnectionKeptAlive;
    @Getter(AccessLevel.NONE)
    private final Map<Class<?>, Object> attributes = new HashMap<>();

    public <T> void addAttribute(Class<T> clazz, T value) {
        attributes.put(clazz, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(Class<T> clazz) {
        return (T) attributes.get(clazz);
    }
}
