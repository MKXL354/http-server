package org.example.model.response;

import java.util.Arrays;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public enum HttpResponseStatus {

    OK(200);

    private final int code;

    HttpResponseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static HttpResponseStatus getByCode(int code) {
        return Arrays.stream(HttpResponseStatus.values()).filter(v -> v.code == code).findFirst().orElse(null);
    }
}
