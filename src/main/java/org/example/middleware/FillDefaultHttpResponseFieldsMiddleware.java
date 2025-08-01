package org.example.middleware;

import org.example.annotation.Middleware;
import org.example.model.enumeration.HttpHeader;
import org.example.model.enumeration.HttpResponseStatus;
import org.example.model.enumeration.header.HttpContentType;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;
import org.example.model.response.StatusLine;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
@Middleware(order = Integer.MAX_VALUE)
public class FillDefaultHttpResponseFieldsMiddleware extends PreProcessMiddleware {

    @Override
    public void preProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        StatusLine statusLine = new StatusLine(httpRequest.getRequestLine().getHttpVersion(), HttpResponseStatus.OK);
        httpResponse.setStatusLine(statusLine);
        httpResponse.getHeaders().addHeader(HttpHeader.CONTENT_TYPE, HttpContentType.PLAIN_TEXT.getValue());
    }
}
