package org.example.model.enumeration;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Getter
public enum HttpResponseStatus {

    OK(200, "Ok"),
    BAD_REQUEST(400, "Bad Request"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String reasonPhrase;

    HttpResponseStatus(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public static HttpResponseStatus getByCode(int code) {
        return Arrays.stream(HttpResponseStatus.values()).filter(v -> v.code == code).findFirst().orElse(null);
    }

    public static HttpResponseStatus getByReasonPhrase(String reasonPhrase) {
        return Arrays.stream(HttpResponseStatus.values()).filter(v -> v.reasonPhrase.equals(reasonPhrase)).findFirst().orElse(null);
    }
}
