package com.mahdy.httpserver.core.model.enumeration;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Getter
public enum HttpVersion {

    HTTP1("HTTP/1.0"),
    HTTP1_1("HTTP/1.1"),
    HTTP2("HTTP/2"),
    HTTP3("HTTP/3");

    private final String label;

    HttpVersion(String label) {
        this.label = label;
    }

    public static HttpVersion of(String label) {
        return Arrays.stream(HttpVersion.values()).filter(v -> v.label.equals(label)).findFirst().orElse(null);
    }
}
