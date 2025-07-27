package org.example.model.response;

import lombok.Data;
import org.example.model.HttpBody;
import org.example.model.HttpHeaders;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
public class HttpResponse {

    private final StatusLine statusLine;
    private final HttpHeaders headers;
    private final HttpBody httpBody;
}
