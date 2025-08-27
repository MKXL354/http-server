package com.mahdy.httpserver.core.exceptionhandling;

import com.mahdy.httpserver.core.annotation.ExceptionHandling;
import com.mahdy.httpserver.core.exception.MalformedHttpRequestException;
import com.mahdy.httpserver.core.exception.RequestMethodNotSupportedException;
import com.mahdy.httpserver.core.exception.base.NotFoundException;
import com.mahdy.httpserver.core.model.HttpBody;
import com.mahdy.httpserver.core.model.enumeration.HttpHeader;
import com.mahdy.httpserver.core.model.enumeration.HttpResponseStatus;
import com.mahdy.httpserver.core.model.enumeration.HttpVersion;
import com.mahdy.httpserver.core.model.enumeration.header.HttpContentType;
import com.mahdy.httpserver.core.model.request.HttpRequest;
import com.mahdy.httpserver.core.model.response.HttpResponse;
import com.mahdy.httpserver.core.model.response.StatusLine;
import org.springframework.stereotype.Component;

import static com.mahdy.httpserver.core.model.enumeration.HttpResponseStatus.*;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
@Component
public class DefaultExceptionHandler {

    @ExceptionHandling(Exception.class)
    public void handleException(Exception e, HttpRequest request, HttpResponse response) {
        handleGeneralFormat(request, response, INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getMessage());
    }

    @ExceptionHandling(NotFoundException.class)
    public void handleNotFoundException(NotFoundException e, HttpRequest request, HttpResponse response) {
        handleGeneralFormat(request, response, NOT_FOUND, NOT_FOUND.getMessage());
    }

    @ExceptionHandling(RequestMethodNotSupportedException.class)
    public void handleRequestMethodNotSupportedException(RequestMethodNotSupportedException e, HttpRequest request, HttpResponse response) {
        handleGeneralFormat(request, response, METHOD_NOT_ALLOWED, METHOD_NOT_ALLOWED.getMessage());
    }

    @ExceptionHandling(MalformedHttpRequestException.class)
    public void handleMalformedHttpRequestException(MalformedHttpRequestException e, HttpRequest request, HttpResponse response) {
        handleGeneralFormat(request, response, BAD_REQUEST, BAD_REQUEST.getMessage() + ": " + e.getMessage());
    }

    private void handleGeneralFormat(HttpRequest request, HttpResponse response, HttpResponseStatus responseStatus, String message) {
        HttpVersion httpVersion = request != null ? request.getRequestLine().getHttpVersion() : HttpVersion.HTTP1_1;
        response.setStatusLine(new StatusLine(httpVersion, responseStatus));
        response.setBody(new HttpBody(message));
        response.getHeaders().getHeaderMap().put(HttpHeader.CONTENT_TYPE, HttpContentType.PLAIN_TEXT.getValue());
    }
}
