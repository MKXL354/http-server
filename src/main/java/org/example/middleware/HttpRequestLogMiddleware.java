package org.example.middleware;

import lombok.extern.slf4j.Slf4j;
import org.example.annotation.Middleware;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
@Middleware(order = Integer.MIN_VALUE)
@Slf4j
public class HttpRequestLogMiddleware extends PreProcessMiddleware {

    @Override
    public void preProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        log.info(httpRequest.toString());
    }
}
