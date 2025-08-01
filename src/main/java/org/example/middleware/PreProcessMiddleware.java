package org.example.middleware;

import lombok.Getter;
import lombok.Setter;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
public abstract class PreProcessMiddleware implements ProcessMiddleware {

    @Getter
    @Setter
    private ProcessMiddleware next;

    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        preProcess(httpRequest, httpResponse);
        if (next != null) {
            next.process(httpRequest, httpResponse);
        }
    }

    public abstract void preProcess(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception;
}
