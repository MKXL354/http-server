package com.mahdy.httpserver.sampleclient.exceptionhandling;

import com.mahdy.httpserver.core.annotation.ExceptionHandling;
import com.mahdy.httpserver.core.exception.base.NotFoundException;
import com.mahdy.httpserver.core.model.HttpBody;
import com.mahdy.httpserver.core.model.enumeration.HttpHeader;
import com.mahdy.httpserver.core.model.enumeration.HttpResponseStatus;
import com.mahdy.httpserver.core.model.enumeration.HttpVersion;
import com.mahdy.httpserver.core.model.request.HttpRequest;
import com.mahdy.httpserver.core.model.response.HttpResponse;
import com.mahdy.httpserver.core.model.response.StatusLine;
import com.mahdy.httpserver.sampleclient.model.CustomContentTypes;
import com.mahdy.httpserver.sampleclient.model.CustomHttpResponseStatus;
import org.springframework.stereotype.Component;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
@Component
public class SampleExceptionHandler {

    @ExceptionHandling(NotFoundException.class)
    public void handleNotFound(NotFoundException e, HttpRequest request, HttpResponse response) {
        HttpResponseStatus responseStatus = CustomHttpResponseStatus.CUSTOM_NOT_FOUND;
        HttpVersion httpVersion = request != null ? request.getRequestLine().getHttpVersion() : HttpVersion.HTTP1_1;
        response.setStatusLine(new StatusLine(httpVersion, responseStatus));
        response.setBody(new HttpBody(responseStatus.getMessage()));
        response.getHeaders().getHeaderMap().put(HttpHeader.CONTENT_TYPE, CustomContentTypes.CUSTOM_PLAIN_TEXT.getValue());
    }
}
