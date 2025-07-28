package org.example.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ApplicationRuntimeException;
import org.example.model.HttpBody;
import org.example.model.enumeration.HttpResponseStatus;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;
import org.example.model.response.StatusLine;
import org.example.processor.request.HttpRequestReader;
import org.example.processor.response.HttpResponseWriter;
import org.example.requestMapping.HandlerMethod;
import org.example.requestMapping.RoutingRegistry;
import org.example.socket.ClientSocket;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HttpHandlerImpl implements HttpHandler {

    private final RoutingRegistry routingRegistry;
    private final HttpRequestReader httpRequestReader;
    private final HttpResponseWriter httpResponseWriter;

    @Override
    public void handle(ClientSocket clientSocket) {
//        TODO: implement keep-alive logic based on headers? conflict with current processor model maybe
        try (clientSocket) {
            HttpRequest httpRequest = httpRequestReader.readHttpRequest(clientSocket);
            log.info(httpRequest.toString());
//            TODO: processor and registry for proper response resolution
            HandlerMethod handlerMethod = routingRegistry.getHandler(httpRequest.getRequestLine().getHttpMethod(),
                    httpRequest.getRequestLine().getRequestPath().getPath());
            Object result = handlerMethod.invoke(httpRequest);
            HttpResponse httpResponse = getHttpResponse(httpRequest, result);
            log.info(httpResponse.toString());
            httpResponseWriter.writeHttpResponse(httpResponse, clientSocket);
        } catch (ApplicationRuntimeException | IOException e) {
            log.warn(e.toString());
        } catch (Exception e) {
            log.warn(e.toString());
        }
    }

    private HttpResponse getHttpResponse(HttpRequest httpRequest, Object result) throws IOException {
//        TODO: actual processing of this for later
        StatusLine statusLine = new StatusLine(httpRequest.getRequestLine().getHttpVersion(), HttpResponseStatus.OK);
        HttpBody httpBody = new HttpBody(result.toString());
        return new HttpResponse(statusLine, null, httpBody);
    }
}
