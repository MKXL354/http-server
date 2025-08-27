package com.mahdy.httpserver.core.model.enumeration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public final class HttpResponseStatus {

    private static final Map<HttpResponseStatusKey, HttpResponseStatus> REGISTRY = new ConcurrentHashMap<>();

    private final HttpResponseStatusKey key;

    private HttpResponseStatus(HttpResponseStatusKey key) {
        this.key = key;
    }

    public static HttpResponseStatus of(int code, String reasonPhrase) {
        String normalized = reasonPhrase.trim().toLowerCase();
        HttpResponseStatusKey responseStatusKey = new HttpResponseStatusKey(code, normalized);
        return REGISTRY.computeIfAbsent(responseStatusKey, key -> new HttpResponseStatus(responseStatusKey));
    }

    public int getCode() {
        return key.getCode();
    }

    public String getReasonPhrase() {
        return key.getReasonPhrase();
    }

    public String getMessage() {
        return key.getCode() + " " + key.getReasonPhrase();
    }

    public static final HttpResponseStatus OK = HttpResponseStatus.of(200, "Ok");
    public static final HttpResponseStatus BAD_REQUEST = HttpResponseStatus.of(400, "Bad Request");
    public static final HttpResponseStatus NOT_FOUND = HttpResponseStatus.of(404, "Not Found");
    public static final HttpResponseStatus METHOD_NOT_ALLOWED = HttpResponseStatus.of(405, "Method Not Allowed");
    public static final HttpResponseStatus INTERNAL_SERVER_ERROR = HttpResponseStatus.of(500, "Internal Server Error");

    @Override
    public String toString() {
        return key.toString();
    }
}
