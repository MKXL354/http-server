package org.example.middleware;

import lombok.extern.slf4j.Slf4j;
import org.example.annotation.Middleware;
import org.example.model.enumeration.HttpHeader;
import org.example.model.enumeration.HttpVersion;
import org.example.model.enumeration.header.HttpConnection;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
@Middleware(order = Integer.MIN_VALUE)
@Slf4j
public class FillHttpRequestContextMiddleware extends PreProcessMiddleware {

    @Override
    public void preProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpConnection connectionHeaderValue = HttpConnection.getByValue(httpRequest.getHeaders().getHeaderMap().get(HttpHeader.CONNECTION));
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
