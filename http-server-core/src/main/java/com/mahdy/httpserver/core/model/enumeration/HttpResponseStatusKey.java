package com.mahdy.httpserver.core.model.enumeration;

import lombok.Data;

/**
 * @author Mehdi Kamali
 * @since 22/08/2025
 */
@Data
public class HttpResponseStatusKey {

    private final int code;
    private final String reasonPhrase;
}
