package org.example.exceptionHandling;

import org.example.annotation.ExceptionHandling;
import org.example.model.HttpBody;
import org.example.model.enumeration.HttpResponseStatus;
import org.example.model.enumeration.HttpVersion;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;
import org.example.model.response.StatusLine;
import org.springframework.stereotype.Component;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
@Component
public class GlobalExceptionHandler {

    @ExceptionHandling(Exception.class)
    public void handleException(Exception e, HttpRequest request, HttpResponse response) {
        HttpVersion httpVersion = request != null ? request.getRequestLine().getHttpVersion() : HttpVersion.HTTP1_1;
        response.setStatusLine(new StatusLine(httpVersion, HttpResponseStatus.INTERNAL_SERVER_ERROR));
        response.setBody(new HttpBody("Internal Server Error"));
    }
}
