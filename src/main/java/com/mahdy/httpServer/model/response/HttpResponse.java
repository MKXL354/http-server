package com.mahdy.httpServer.model.response;

import com.mahdy.httpServer.model.HttpBody;
import com.mahdy.httpServer.model.HttpHeaders;
import lombok.AllArgsConstructor;
import lombok.Data;

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
