package org.example.model.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
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
    @Setter(AccessLevel.NONE)
    private HttpHeaders headers;
    private HttpBody body;
}
