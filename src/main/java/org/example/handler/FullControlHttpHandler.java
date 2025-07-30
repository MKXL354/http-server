package org.example.handler;

import org.example.exception.ProcessorMethodNotFoundException;
import org.example.io.request.HttpRequestReader;
import org.example.io.response.HttpResponseWriter;
import org.example.model.HttpHeaders;
import org.example.model.enumeration.HttpResponseStatus;
import org.example.model.request.HttpRequest;
import org.example.model.request.RequestLine;
import org.example.model.response.HttpResponse;
import org.example.model.response.StatusLine;
import org.example.routing.ProcessorMethod;
import org.example.routing.RoutingRegistry;
import org.springframework.stereotype.Component;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class FullControlHttpHandler extends HttpHandlerTemplate {

    private final RoutingRegistry routingRegistry;

    public FullControlHttpHandler(RoutingRegistry routingRegistry, HttpRequestReader httpRequestReader, HttpResponseWriter httpResponseWriter) {
        super(httpRequestReader, httpResponseWriter);
        this.routingRegistry = routingRegistry;
    }

    @Override
    public HttpResponse getHttpResponse(HttpRequest httpRequest) throws Exception {
        RequestLine requestLine = httpRequest.getRequestLine();
        ProcessorMethod processorMethod = routingRegistry.getHandler(requestLine.getHttpMethod(),
                requestLine.getRequestPath().getPath());
        StatusLine statusLine = new StatusLine(requestLine.getHttpVersion(), HttpResponseStatus.OK);
        HttpResponse httpResponse = new HttpResponse(statusLine, new HttpHeaders(), null);
        if (processorMethod == null) {
            throw new ProcessorMethodNotFoundException();
        }
        processorMethod.invoke(httpRequest, httpResponse);
        return httpResponse;
    }
}
