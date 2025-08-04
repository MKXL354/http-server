package org.example.server.lifeCycle;

import org.example.exceptionHandling.ExceptionHandlingRegistry;
import org.example.io.request.HttpRequestReader;
import org.example.io.response.HttpResponseWriter;
import org.example.middleware.MiddlewareRegistry;
import org.example.model.HandlerMethod;
import org.example.model.request.HttpRequest;
import org.example.model.request.RequestLine;
import org.example.model.request.RequestPath;
import org.example.model.response.HttpResponse;
import org.example.routing.RoutingRegistry;
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
