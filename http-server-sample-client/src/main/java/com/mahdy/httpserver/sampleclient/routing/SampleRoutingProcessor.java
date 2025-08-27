package com.mahdy.httpserver.sampleclient.routing;

import com.mahdy.httpserver.core.annotation.Routing;
import com.mahdy.httpserver.core.model.HttpBody;
import com.mahdy.httpserver.core.model.enumeration.HttpMethod;
import com.mahdy.httpserver.core.model.request.HttpRequest;
import com.mahdy.httpserver.core.model.response.HttpResponse;
import org.springframework.stereotype.Component;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
@Component
public class SampleRoutingProcessor {

    @Routing(httpMethod = HttpMethod.GET, path = "/hello")
    public void getHello(HttpRequest request, HttpResponse response) {
        response.setBody(new HttpBody("Hello From Client!"));
    }
}
