package com.mahdy.httpserver.core.model.response;

import com.mahdy.httpserver.core.model.enumeration.HttpResponseStatus;
import com.mahdy.httpserver.core.model.enumeration.HttpVersion;
import lombok.AllArgsConstructor;
import lombok.Data;

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
