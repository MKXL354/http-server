package com.mahdy.httpServer.server.lifeCycle;

import com.mahdy.httpServer.exceptionHandling.ExceptionHandlingRegistry;
import com.mahdy.httpServer.io.request.HttpRequestReader;
import com.mahdy.httpServer.io.response.HttpResponseWriter;
import com.mahdy.httpServer.model.HandlerMethod;
import com.mahdy.httpServer.model.enumeration.HttpHeader;
import com.mahdy.httpServer.model.request.HttpRequest;
import com.mahdy.httpServer.model.response.HttpResponse;
import com.mahdy.httpServer.server.socket.ClientSocket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@RequiredArgsConstructor
@Slf4j
public abstract class HttpLifeCycleTemplate {

    private final HttpRequestReader httpRequestReader;
    private final HttpResponseWriter httpResponseWriter;
    private final ExceptionHandlingRegistry exceptionHandlingRegistry;

    public void executeLifeCycle(ClientSocket clientSocket) {
        while (true) {
            HttpRequest httpRequest = null;
            HttpResponse httpResponse = new HttpResponse(null, null);
            try {
                httpRequest = httpRequestReader.readHttpRequest(clientSocket);
                if (httpRequest == null) {
                    break;
                }
                handleHttpResponse(httpRequest, httpResponse);
                boolean isConnectionKeptAlive = httpRequest.getHttpContext().isConnectionKeptAlive();
                httpResponseWriter.writeHttpResponse(httpResponse, clientSocket);
                if (!isConnectionKeptAlive) {
                    break;
                }
            } catch (IOException e) {
                log.warn("caught socket exception, breaking request loop", e);
                break;
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                handleException(e, httpRequest, httpResponse, clientSocket);
            }
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            log.warn("could not close socket", e);
        }
    }

    private void handleException(Exception e, HttpRequest httpRequest, HttpResponse httpResponse, ClientSocket clientSocket) {
        HandlerMethod exceptionHandler = exceptionHandlingRegistry.getHandler(e.getClass());
        exceptionHandler.invokeWithoutResults(e, httpRequest, httpResponse);
        httpResponse.getHeaders().getHeaderMap().put(HttpHeader.CONTENT_LENGTH, String.valueOf(httpResponse.getBody().getBodyAsBytes().length));
        try {
            httpResponseWriter.writeHttpResponse(httpResponse, clientSocket);
        } catch (IOException ex) {
            log.warn("caught socket exception, breaking request loop", ex);
        }
    }

    public abstract void handleHttpResponse(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception;
}
