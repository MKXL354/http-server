package org.example.model.response;

import lombok.Data;
import org.example.model.enumeration.HttpResponseStatus;
import org.example.model.enumeration.HttpVersion;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
public class StatusLine {

    private final HttpVersion httpVersion;
    private final HttpResponseStatus httpResponseStatus;
}
