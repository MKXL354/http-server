package org.example.server.lifeCycle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptionHandling.ExceptionHandlingRegistry;
import org.example.io.request.HttpRequestReader;
import org.example.io.response.HttpResponseWriter;
import org.example.model.HandlerMethod;
import org.example.model.HttpHeaders;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;
import org.example.server.socket.ClientSocket;

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
            HttpResponse httpResponse = new HttpResponse(null, new HttpHeaders(), null);
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
        HandlerMethod handlerMethod = exceptionHandlingRegistry.getHandler(e.getClass());
        handlerMethod.invokeWithoutResults(e, httpRequest, httpResponse);
        try {
            httpResponseWriter.writeHttpResponse(httpResponse, clientSocket);
        } catch (IOException ex) {
            log.warn("caught socket exception, breaking request loop", ex);
        }
    }

    public abstract void handleHttpResponse(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception;
}
