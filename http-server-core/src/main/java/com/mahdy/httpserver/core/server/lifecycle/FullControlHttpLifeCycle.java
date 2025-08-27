package com.mahdy.httpserver.core.server.lifecycle;

import com.mahdy.httpserver.core.exceptionhandling.ExceptionHandlingRegistry;
import com.mahdy.httpserver.core.io.request.HttpRequestReader;
import com.mahdy.httpserver.core.io.response.HttpResponseWriter;
import com.mahdy.httpserver.core.middleware.MiddlewareRegistry;
import com.mahdy.httpserver.core.model.HandlerMethod;
import com.mahdy.httpserver.core.model.request.HttpRequest;
import com.mahdy.httpserver.core.model.request.RequestLine;
import com.mahdy.httpserver.core.model.request.RequestPath;
import com.mahdy.httpserver.core.model.response.HttpResponse;
import com.mahdy.httpserver.core.routing.RoutingRegistry;
import org.springframework.stereotype.Component;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class FullControlHttpLifeCycle extends HttpLifeCycleTemplate {

    private final RoutingRegistry routingRegistry;
    private final MiddlewareRegistry middlewareRegistry;

    public FullControlHttpLifeCycle(RoutingRegistry routingRegistry, MiddlewareRegistry middlewareRegistry,
                                    ExceptionHandlingRegistry exceptionHandlingRegistry, HttpRequestReader httpRequestReader,
                                    HttpResponseWriter httpResponseWriter) {
        super(httpRequestReader, httpResponseWriter, exceptionHandlingRegistry);
        this.routingRegistry = routingRegistry;
        this.middlewareRegistry = middlewareRegistry;
    }

    @Override
    public void handleHttpResponse(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        RequestLine requestLine = httpRequest.getRequestLine();
        RequestPath requestPath = requestLine.getRequestPath();
        HandlerMethod handlerMethod = routingRegistry.getHandler(requestLine.getHttpMethod(), requestPath);
        middlewareRegistry.getPreProcessMiddlewareChainStart().process(httpRequest, httpResponse);
        handlerMethod.invokeWithResults(httpRequest, httpResponse);
        middlewareRegistry.getPostProcessMiddlewareChainStart().process(httpRequest, httpResponse);
    }
}
