package com.mahdy.httpServer.server.lifeCycle;

import com.mahdy.httpServer.exceptionHandling.ExceptionHandlingRegistry;
import com.mahdy.httpServer.io.request.HttpRequestReader;
import com.mahdy.httpServer.io.response.HttpResponseWriter;
import com.mahdy.httpServer.middleware.MiddlewareRegistry;
import com.mahdy.httpServer.model.HandlerMethod;
import com.mahdy.httpServer.model.request.HttpRequest;
import com.mahdy.httpServer.model.request.RequestLine;
import com.mahdy.httpServer.model.request.RequestPath;
import com.mahdy.httpServer.model.response.HttpResponse;
import com.mahdy.httpServer.routing.RoutingRegistry;
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
