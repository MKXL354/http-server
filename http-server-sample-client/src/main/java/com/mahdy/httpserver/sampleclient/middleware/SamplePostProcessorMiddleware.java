package com.mahdy.httpserver.sampleclient.middleware;

import com.mahdy.httpserver.core.annotation.Middleware;
import com.mahdy.httpserver.core.middleware.PostProcessMiddleware;
import com.mahdy.httpserver.core.model.request.HttpRequest;
import com.mahdy.httpserver.core.model.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
@Component
@Middleware(order = Integer.MAX_VALUE)
@Slf4j
public class SamplePostProcessorMiddleware extends PostProcessMiddleware {

    @Override
    public void postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        log.info("response is\n{}", httpResponse);
    }
}
