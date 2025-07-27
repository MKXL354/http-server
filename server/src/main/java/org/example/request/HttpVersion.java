package org.example.request;

import java.util.Arrays;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public enum HttpVersion {
    HTTP1("HTTP/1.0"),
    HTTP1_1("HTTP/1.1"),
    HTTP2("HTTP/2"),
    HTTP3("HTTP/3");

    private final String value;

    HttpVersion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public HttpVersion getByValue(String value) {
        return Arrays.stream(HttpVersion.values()).filter(v -> v.value.equals(value)).findFirst().orElse(null);
    }
}
