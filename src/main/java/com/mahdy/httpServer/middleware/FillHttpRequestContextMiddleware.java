package com.mahdy.httpServer.middleware;

import com.mahdy.httpServer.annotation.Middleware;
import com.mahdy.httpServer.model.enumeration.HttpHeader;
import com.mahdy.httpServer.model.enumeration.HttpVersion;
import com.mahdy.httpServer.model.enumeration.header.HttpConnection;
import com.mahdy.httpServer.model.request.HttpRequest;
import com.mahdy.httpServer.model.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
@Middleware(order = Integer.MIN_VALUE)
@Slf4j
public class FillHttpRequestContextMiddleware extends PreProcessMiddleware {

    @Override
    public void preProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpConnection connectionHeaderValue = HttpConnection.of(httpRequest.getHeaders().getHeaderMap().get(HttpHeader.CONNECTION));
        boolean isConnectionKeptAlive;
        if (connectionHeaderValue == null) {
            isConnectionKeptAlive = !httpRequest.getRequestLine().getHttpVersion().equals(HttpVersion.HTTP1);
        } else {
            isConnectionKeptAlive = connectionHeaderValue.equals(HttpConnection.KEEP_ALIVE);
        }
        httpRequest.getHttpContext().setConnectionKeptAlive(isConnectionKeptAlive);
        log.info(httpRequest.toString());
    }
}
