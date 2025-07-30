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
public class FullControlProcessor {

    @Routing(httpMethod = HttpMethod.GET, path = "/")
    public void getSimpleHttpResponse(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setBody(new HttpBody(httpRequest.toString()));
    }

    @Routing(httpMethod = HttpMethod.POST, path = "/")
    public void postSimpleHttpResponse(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setBody(new HttpBody(httpRequest.toString()));
    }
}
