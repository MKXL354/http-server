package org.example.controller;

import org.example.model.enumeration.HttpMethod;
import org.example.model.request.HttpRequest;
import org.example.requestMapping.annotation.Routing;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class SimpleController {

    @Routing(httpMethod = HttpMethod.GET, path = "/")
    public String getSimpleHttpResponse(HttpRequest httpRequest) {
        return httpRequest.toString();
    }

    @Routing(httpMethod = HttpMethod.POST, path = "/")
    public String postSimpleHttpResponse(HttpRequest httpRequest) {
        return httpRequest.toString();
    }
}
