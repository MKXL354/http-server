package org.example.routing;

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
//        TODO: NIO util read index.html file?
        httpResponse.setBody(new HttpBody(httpRequest.toString()));
    }

    @Routing(httpMethod = HttpMethod.POST, path = "/")
    public void processSimplePost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    @Routing(httpMethod = HttpMethod.GET, path = "/favicon.ico")
    public void processGetFavIcon(HttpRequest httpRequest, HttpResponse httpResponse) {
//        TODO: binary data here? requires HttpRequest change of structure and writer
    }
}
