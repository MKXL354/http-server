package org.example.exceptionHandling;

import org.example.annotation.ExceptionHandling;
import org.example.exception.RequestMethodNotSupportedException;
import org.example.exception.RequestPathNotFoundException;
import org.example.model.HttpBody;
import org.example.model.enumeration.HttpResponseStatus;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;
import org.example.model.response.StatusLine;
import org.springframework.stereotype.Component;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
@Component
public class DefaultExceptionHandler {

    @ExceptionHandling(Exception.class)
    public void handleException(Exception e, HttpRequest request, HttpResponse response) {
        response.setStatusLine(new StatusLine(request.getRequestLine().getHttpVersion(), HttpResponseStatus.INTERNAL_SERVER_ERROR));
        response.setBody(new HttpBody(HttpResponseStatus.INTERNAL_SERVER_ERROR.getMessage()));
    }

    @ExceptionHandling(RequestPathNotFoundException.class)
    public void handleProcessorMethodNotFoundException(RequestPathNotFoundException e, HttpRequest request, HttpResponse response) {
        response.setStatusLine(new StatusLine(request.getRequestLine().getHttpVersion(), HttpResponseStatus.NOT_FOUND));
        response.setBody(new HttpBody(HttpResponseStatus.NOT_FOUND.getMessage()));
    }

    @ExceptionHandling(RequestMethodNotSupportedException.class)
    public void handleRequestMethodNotSupportedException(RequestMethodNotSupportedException e, HttpRequest request, HttpResponse response) {
        response.setStatusLine(new StatusLine(request.getRequestLine().getHttpVersion(), HttpResponseStatus.METHOD_NOT_ALLOWED));
        response.setBody(new HttpBody(HttpResponseStatus.METHOD_NOT_ALLOWED.getMessage()));
    }
}
