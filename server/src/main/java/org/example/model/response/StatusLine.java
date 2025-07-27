package org.example.model.response;

import lombok.Data;
import org.example.model.HttpVersion;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
public class StatusLine {

    private final HttpVersion httpVersion;
    private final HttpResponseStatus httpResponseStatus;
}
