package com.mahdy.httpServer.model.enumeration.header;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author Mehdi Kamali
 * @since 28/07/2025
 */
@Getter
public enum HttpConnection {

    KEEP_ALIVE("keep-alive"),
    CLOSE("close");

    private final String value;

    HttpConnection(String value) {
        this.value = value;
    }

    public static HttpConnection getByValue(String value) {
        return Arrays.stream(HttpConnection.values()).filter(v -> v.value.equals(value)).findFirst().orElse(null);
    }
}
