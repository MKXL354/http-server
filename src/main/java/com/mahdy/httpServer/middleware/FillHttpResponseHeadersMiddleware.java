package com.mahdy.httpServer.middleware;

import com.mahdy.httpServer.annotation.Middleware;
import com.mahdy.httpServer.model.enumeration.HttpHeader;
import com.mahdy.httpServer.model.enumeration.header.HttpConnection;
import com.mahdy.httpServer.model.request.HttpRequest;
import com.mahdy.httpServer.model.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
@Middleware(order = Integer.MIN_VALUE)
@Slf4j
public class FillHttpResponseHeadersMiddleware extends PostProcessMiddleware {

    @Override
    public void postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] bodyBytes = httpResponse.getBody().getBodyAsBytes();
        Map<HttpHeader, String> headerMap = httpResponse.getHeaders().getHeaderMap();
        headerMap.put(HttpHeader.CONTENT_LENGTH, String.valueOf(bodyBytes.length));
        headerMap.put(HttpHeader.CONNECTION, httpRequest.getHttpContext().isConnectionKeptAlive() ?
                HttpConnection.KEEP_ALIVE.getValue() : HttpConnection.CLOSE.getValue());
        log.info(httpResponse.toString());
    }
}
