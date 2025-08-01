package org.example.middleware;

import org.example.annotation.Middleware;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
@Middleware(order = Integer.MIN_VALUE)
public class DefaultPostProcessMiddleware extends PostProcessMiddleware {

    @Override
    public void postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
//TODO: remove later when request context is introduced and needed to be filled up and logged
