package com.mahdy.httpServer.model.enumeration.header;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mehdi Kamali
 * @since 28/07/2025
 */
@EqualsAndHashCode
public final class HttpContentType {

    private static final Map<String, HttpContentType> REGISTRY = new ConcurrentHashMap<>();

    @Getter
    private final String value;

    private HttpContentType(String value) {
        this.value = value;
    }

    public static HttpContentType of(String value) {
        String normalized = value.trim().toLowerCase();
        return REGISTRY.computeIfAbsent(normalized, HttpContentType::new);
    }

    public static final HttpContentType PLAIN_TEXT = HttpContentType.of("text/plain");
    public static final HttpContentType HTML = HttpContentType.of("text/html");
    public static final HttpContentType IMAGE_X_ICON = HttpContentType.of("image/x-icon");

    @Override
    public String toString() {
        return value;
    }
}
