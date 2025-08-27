package com.mahdy.httpserver.core.routing;

import com.mahdy.httpserver.core.annotation.Routing;
import com.mahdy.httpserver.core.exception.ResourceNotFoundException;
import com.mahdy.httpserver.core.model.HttpBody;
import com.mahdy.httpserver.core.model.enumeration.HttpHeader;
import com.mahdy.httpserver.core.model.enumeration.HttpMethod;
import com.mahdy.httpserver.core.model.enumeration.header.HttpContentType;
import com.mahdy.httpserver.core.model.request.HttpRequest;
import com.mahdy.httpserver.core.model.response.HttpResponse;
import com.mahdy.httpserver.core.util.CommonUtils;

import java.nio.file.Files;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class SimpleFullControlProcessor {

    private final String STATIC_RESOURCE_DIRECTORY = "web/";

    @Routing(httpMethod = HttpMethod.GET, path = "/request")
    public void processSimpleGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setBody(new HttpBody(httpRequest.toString()));
    }

    @Routing(httpMethod = HttpMethod.GET, path = "/query")
    public void processQueryParams(HttpRequest httpRequest, HttpResponse httpResponse) {
        StringBuilder queryParams = new StringBuilder();
        httpRequest.getRequestLine().getRequestPath().getQueryParameters().forEach((key, value) -> {
            queryParams.append(key).append(" = ").append(value).append("\r\n");
        });
        httpResponse.setBody(new HttpBody(queryParams.toString()));
    }

    @Routing(httpMethod = HttpMethod.GET, path = "/variable/{variable}/value/{value}")
    public void processPathVariables(HttpRequest httpRequest, HttpResponse httpResponse) {
        StringBuilder pathVariables = new StringBuilder();
        httpRequest.getRequestLine().getRequestPath().getPathVariables().forEach((key, value) -> {
            pathVariables.append("{").append(key).append("}").append(" -> ").append(value).append("\r\n");
        });
        httpResponse.setBody(new HttpBody(pathVariables.toString()));
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
        try {
            return Files.readAllBytes(CommonUtils.getResourceAsPath(STATIC_RESOURCE_DIRECTORY + fileName));
        } catch (Exception e) {
            throw new ResourceNotFoundException(e);
        }
    }
}
