package com.mahdy.httpServer.routing;

import com.mahdy.httpServer.annotation.Routing;
import com.mahdy.httpServer.exception.ResourceNotFoundException;
import com.mahdy.httpServer.model.HttpBody;
import com.mahdy.httpServer.model.enumeration.HttpHeader;
import com.mahdy.httpServer.model.enumeration.HttpMethod;
import com.mahdy.httpServer.model.enumeration.header.HttpContentType;
import com.mahdy.httpServer.model.request.HttpRequest;
import com.mahdy.httpServer.model.response.HttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class SimpleFullControlProcessor {

    @Routing(httpMethod = HttpMethod.GET, path = "/")
    public void processSimpleGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setBody(new HttpBody(httpRequest.toString()));
    }

    @Routing(httpMethod = HttpMethod.GET, path = "/index.html")
    public void processGetIndexHtml(HttpRequest httpRequest, HttpResponse httpResponse) throws ResourceNotFoundException {
        byte[] bytes = readStaticResource("index.html");
        httpResponse.setBody(new HttpBody(bytes));
        httpResponse.getHeaders().getHeaderMap().put(HttpHeader.CONTENT_TYPE, HttpContentType.HTML.getValue());
    }

    @Routing(httpMethod = HttpMethod.GET, path = "/favicon.ico")
    public void processGetFavIcon(HttpRequest httpRequest, HttpResponse httpResponse) throws ResourceNotFoundException {
        byte[] bytes = readStaticResource("favicon.ico");
        httpResponse.setBody(new HttpBody(bytes));
        httpResponse.getHeaders().getHeaderMap().put(HttpHeader.CONTENT_TYPE, HttpContentType.IMAGE_X_ICON.getValue());
    }

    private byte[] readStaticResource(String fileName) throws ResourceNotFoundException {
        URL resource = getClass().getClassLoader().getResource("web/" + fileName);
        if (resource == null) {
            throw new ResourceNotFoundException();
        }
        try {
            Path path = Paths.get(resource.toURI());
            return Files.readAllBytes(path);
        } catch (IOException | URISyntaxException e) {
            throw new ResourceNotFoundException(e);
        }
    }
}
