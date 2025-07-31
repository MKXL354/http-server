package org.example.lifeCycle;

import org.example.exception.RequestMethodNotSupportedException;
import org.example.exception.RequestPathNotFoundException;
import org.example.exceptionHandling.ExceptionHandlingRegistry;
import org.example.io.request.HttpRequestReader;
import org.example.io.response.HttpResponseWriter;
import org.example.model.HandlerMethod;
import org.example.model.enumeration.HttpResponseStatus;
import org.example.model.request.HttpRequest;
import org.example.model.request.RequestLine;
import org.example.model.response.HttpResponse;
import org.example.model.response.StatusLine;
import org.example.routing.RoutingRegistry;
import org.springframework.stereotype.Component;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class FullControlHttpLifeCycle extends HttpLifeCycleTemplate {

    private final RoutingRegistry routingRegistry;

    public FullControlHttpLifeCycle(RoutingRegistry routingRegistry, ExceptionHandlingRegistry exceptionHandlingRegistry,
                                    HttpRequestReader httpRequestReader, HttpResponseWriter httpResponseWriter) {
        super(httpRequestReader, httpResponseWriter, exceptionHandlingRegistry);
        this.routingRegistry = routingRegistry;
    }

    @Override
    public void handleHttpResponse(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        RequestLine requestLine = httpRequest.getRequestLine();
        if (!routingRegistry.isPathExist(requestLine.getRequestPath().getPathString())) {
            throw new RequestPathNotFoundException();
        }
        HandlerMethod handlerMethod = routingRegistry.getHandler(requestLine.getHttpMethod(),
                requestLine.getRequestPath().getPathString());
        if (handlerMethod == null) {
            throw new RequestMethodNotSupportedException();
        }
        //        TODO: this filling of http request can be moved in to a pre middleware
        StatusLine statusLine = new StatusLine(requestLine.getHttpVersion(), HttpResponseStatus.OK);
        httpResponse.setStatusLine(statusLine);
        handlerMethod.invokeWithResults(httpRequest, httpResponse);
    }
}
