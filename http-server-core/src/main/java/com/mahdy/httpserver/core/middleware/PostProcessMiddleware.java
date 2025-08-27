package com.mahdy.httpserver.core.middleware;

import com.mahdy.httpserver.core.model.request.HttpRequest;
import com.mahdy.httpserver.core.model.response.HttpResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
public abstract class PostProcessMiddleware implements ProcessMiddleware {

    @Getter
    @Setter
    private ProcessMiddleware next;

    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        postProcess(httpRequest, httpResponse);
        if (next != null) {
            next.process(httpRequest, httpResponse);
        }
    }

    public abstract void postProcess(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception;

}
