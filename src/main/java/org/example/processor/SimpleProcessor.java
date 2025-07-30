package org.example.processor;

import org.example.model.enumeration.HttpMethod;
import org.example.model.request.HttpRequest;
import org.example.routing.annotation.Routing;
import org.springframework.stereotype.Component;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class SimpleProcessor {

    @Routing(httpMethod = HttpMethod.GET, path = "/")
    public String getSimpleHttpResponse(HttpRequest httpRequest) {
        return httpRequest.toString();
    }

    @Routing(httpMethod = HttpMethod.POST, path = "/")
    public String postSimpleHttpResponse(HttpRequest httpRequest) {
        return httpRequest.toString();
    }
}
