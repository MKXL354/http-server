package com.mahdy.httpServer.middleware;

import com.mahdy.httpServer.annotation.Middleware;
import com.mahdy.httpServer.model.HttpBody;
import com.mahdy.httpServer.model.enumeration.HttpHeader;
import com.mahdy.httpServer.model.enumeration.HttpResponseStatus;
import com.mahdy.httpServer.model.enumeration.header.HttpContentType;
import com.mahdy.httpServer.model.request.HttpRequest;
import com.mahdy.httpServer.model.response.HttpResponse;
import com.mahdy.httpServer.model.response.StatusLine;

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
