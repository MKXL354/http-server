package org.example.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.enumeration.HttpResponseStatus;
import org.example.model.enumeration.HttpVersion;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
@AllArgsConstructor
public class StatusLine {

    private HttpVersion httpVersion;
    private HttpResponseStatus httpResponseStatus;
}
