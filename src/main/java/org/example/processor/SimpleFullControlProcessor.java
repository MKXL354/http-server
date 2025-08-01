package org.example.processor;

import org.example.annotation.Routing;
import org.example.model.HttpBody;
import org.example.model.enumeration.HttpMethod;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;
import org.springframework.stereotype.Component;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class SimpleFullControlProcessor {

    @Routing(httpMethod = HttpMethod.GET, path = "/")
    public void processSimpleGet(HttpRequest httpRequest, HttpResponse httpResponse) {
//        TODO: remove the rest and use util to read index.html file NIO fashion
        httpResponse.setBody(new HttpBody(httpRequest.toString()));
    }

    @Routing(httpMethod = HttpMethod.POST, path = "/")
    public void processSimplePost(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setBody(new HttpBody(httpRequest.toString()));
    }

    @Routing(httpMethod = HttpMethod.DELETE, path = "/")
    public void processSimpleDelete(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    @Routing(httpMethod = HttpMethod.GET, path = "/favicon.ico")
    public void processGetFavIcon(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
//TODO: maybe rename these to plain handler names? handler and exception handler
