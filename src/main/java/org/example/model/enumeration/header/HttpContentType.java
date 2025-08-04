package org.example.model.enumeration.header;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author Mehdi Kamali
 * @since 28/07/2025
 */
@Getter
public enum HttpContentType {

    PLAIN_TEXT("text/plain"),
    IMAGE_X_ICON("image/x-icon"),
    HTML("text/html");

    private final String value;

    HttpContentType(String value) {
        this.value = value;
    }

    public static HttpContentType getByValue(String value) {
        return Arrays.stream(HttpContentType.values()).filter(v -> v.value.equals(value)).findFirst().orElse(null);
    }
}
