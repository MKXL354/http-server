package org.example.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.HttpBody;
import org.example.model.HttpHeaders;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
@AllArgsConstructor
public class HttpResponse {

    private StatusLine statusLine;
    private final HttpHeaders headers = new HttpHeaders();
    private HttpBody body;
}
