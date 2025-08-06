package com.mahdy.httpServer.exceptionHandling;

import com.mahdy.httpServer.annotation.ExceptionHandling;
import com.mahdy.httpServer.exception.MalformedHttpRequestException;
import com.mahdy.httpServer.exception.RequestMethodNotSupportedException;
import com.mahdy.httpServer.exception.base.NotFoundException;
import com.mahdy.httpServer.model.HttpBody;
import com.mahdy.httpServer.model.enumeration.HttpHeader;
import com.mahdy.httpServer.model.enumeration.HttpResponseStatus;
import com.mahdy.httpServer.model.enumeration.HttpVersion;
import com.mahdy.httpServer.model.enumeration.header.HttpContentType;
import com.mahdy.httpServer.model.request.HttpRequest;
import com.mahdy.httpServer.model.response.HttpResponse;
import com.mahdy.httpServer.model.response.StatusLine;
import org.springframework.stereotype.Component;

import java.util.Map;

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
        Map<HttpHeader, String> headerMap = response.getHeaders().getHeaderMap();
        headerMap.put(HttpHeader.CONTENT_TYPE, HttpContentType.PLAIN_TEXT.getValue());
        headerMap.put(HttpHeader.CONTENT_LENGTH, String.valueOf(response.getBody().getBodyAsBytes().length));
    }
}
//TODO: make changes so that exception handler does not need to write so many things?
