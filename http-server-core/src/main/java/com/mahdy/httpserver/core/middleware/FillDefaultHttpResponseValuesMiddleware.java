package com.mahdy.httpserver.core.middleware;

import com.mahdy.httpserver.core.annotation.Middleware;
import com.mahdy.httpserver.core.model.HttpBody;
import com.mahdy.httpserver.core.model.enumeration.HttpHeader;
import com.mahdy.httpserver.core.model.enumeration.HttpResponseStatus;
import com.mahdy.httpserver.core.model.enumeration.header.HttpContentType;
import com.mahdy.httpserver.core.model.request.HttpRequest;
import com.mahdy.httpserver.core.model.response.HttpResponse;
import com.mahdy.httpserver.core.model.response.StatusLine;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
@Middleware(order = Integer.MAX_VALUE)
public class FillDefaultHttpResponseValuesMiddleware extends PreProcessMiddleware {

    @Override
    public void preProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        StatusLine statusLine = new StatusLine(httpRequest.getRequestLine().getHttpVersion(), HttpResponseStatus.OK);
        httpResponse.setStatusLine(statusLine);
        httpResponse.setBody(new HttpBody(""));
        httpResponse.getHeaders().getHeaderMap().put(HttpHeader.CONTENT_TYPE, HttpContentType.PLAIN_TEXT.getValue());
    }
}
