package org.example.model;

import java.util.Arrays;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public enum HttpHeader {
    HOST("Host"),
    USER_AGENT("User-Agent"),
    ACCEPT("Accept"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type"),
    CONNECTION("Connection");

    private final String value;

    HttpHeader(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static HttpHeader getByValue(String value) {
        return Arrays.stream(HttpHeader.values()).filter(v -> v.value.equals(value)).findFirst().orElse(null);
    }
}
