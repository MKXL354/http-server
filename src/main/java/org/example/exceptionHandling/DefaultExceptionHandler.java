package org.example.exceptionHandling;

import org.example.annotation.ExceptionHandling;
import org.example.exception.MalformedHttpRequestException;
import org.example.exception.RequestMethodNotSupportedException;
import org.example.exception.base.NotFoundException;
import org.example.model.HttpBody;
import org.example.model.enumeration.HttpHeader;
import org.example.model.enumeration.HttpResponseStatus;
import org.example.model.enumeration.HttpVersion;
import org.example.model.enumeration.header.HttpContentType;
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
        handleGeneralFormat(request, response, HttpResponseStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandling(NotFoundException.class)
    public void handleNotFoundException(NotFoundException e, HttpRequest request, HttpResponse response) {
        handleGeneralFormat(request, response, HttpResponseStatus.NOT_FOUND);
    }

    @ExceptionHandling(RequestMethodNotSupportedException.class)
    public void handleRequestMethodNotSupportedException(RequestMethodNotSupportedException e, HttpRequest request, HttpResponse response) {
        handleGeneralFormat(request, response, HttpResponseStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandling(MalformedHttpRequestException.class)
    public void handleMalformedHttpRequestException(MalformedHttpRequestException e, HttpRequest request, HttpResponse response) {
        handleGeneralFormat(request, response, HttpResponseStatus.BAD_REQUEST);
    }

    private void handleGeneralFormat(HttpRequest request, HttpResponse response, HttpResponseStatus responseStatus) {
        HttpVersion httpVersion = request != null ? request.getRequestLine().getHttpVersion() : HttpVersion.HTTP1_1;
        response.setStatusLine(new StatusLine(httpVersion, responseStatus));
        response.setBody(new HttpBody(responseStatus.getMessage()));
        response.getHeaders().addHeader(HttpHeader.CONTENT_TYPE, HttpContentType.PLAIN_TEXT.getValue());
        response.getHeaders().addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(response.getBody().getBodyAsBytes().length));
    }
}
//TODO: make changes so that exception handler does not need to write so many things?
