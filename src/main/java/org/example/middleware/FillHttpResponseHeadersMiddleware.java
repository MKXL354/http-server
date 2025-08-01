package org.example.middleware;

import lombok.extern.slf4j.Slf4j;
import org.example.annotation.Middleware;
import org.example.model.enumeration.HttpHeader;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
@Middleware(order = Integer.MIN_VALUE)
@Slf4j
public class FillHttpResponseHeadersMiddleware extends PostProcessMiddleware {

    @Override
    public void postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        String bodyString = httpResponse.getBody().getBodyString();
        if (StringUtils.hasText(bodyString)) {
            httpResponse.getHeaders().addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(bodyString.getBytes(StandardCharsets.UTF_8).length));
        } else {
            httpResponse.getHeaders().addHeader(HttpHeader.CONTENT_LENGTH, "0");
        }
        log.info(httpResponse.toString());
    }
}
