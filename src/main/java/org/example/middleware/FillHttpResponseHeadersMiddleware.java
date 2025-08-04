package org.example.middleware;

import lombok.extern.slf4j.Slf4j;
import org.example.annotation.Middleware;
import org.example.model.enumeration.HttpHeader;
import org.example.model.enumeration.header.HttpConnection;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;

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
        httpResponse.getHeaders().addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(bodyBytes.length));

        httpResponse.getHeaders().addHeader(HttpHeader.CONNECTION, httpRequest.getHttpContext().isConnectionKeptAlive() ?
                HttpConnection.KEEP_ALIVE.getValue() : HttpConnection.CLOSE.getValue());
        log.info(httpResponse.toString());
    }
}
