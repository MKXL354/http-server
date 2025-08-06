package com.mahdy.httpServer.model.response;

import com.mahdy.httpServer.model.enumeration.HttpResponseStatus;
import com.mahdy.httpServer.model.enumeration.HttpVersion;
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
