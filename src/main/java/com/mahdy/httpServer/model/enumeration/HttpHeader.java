package com.mahdy.httpServer.model.enumeration;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@EqualsAndHashCode
public final class HttpHeader {

    private static final Map<String, HttpHeader> REGISTRY = new ConcurrentHashMap<>();

    @Getter
    private final String value;

    private HttpHeader(String value) {
        this.value = value;
    }

    public static HttpHeader of(String value) {
        String normalized = value.trim().toLowerCase();
        return REGISTRY.computeIfAbsent(normalized, HttpHeader::new);
    }

    public static final HttpHeader HOST = HttpHeader.of("Host");
    public static final HttpHeader USER_AGENT = HttpHeader.of("User-Agent");
    public static final HttpHeader ACCEPT = HttpHeader.of("Accept");
    public static final HttpHeader CONTENT_LENGTH = HttpHeader.of("Content-Length");
    public static final HttpHeader CONTENT_TYPE = HttpHeader.of("Content-Type");
    public static final HttpHeader CONNECTION = HttpHeader.of("Connection");

    @Override
    public String toString() {
        return value;
    }
}
