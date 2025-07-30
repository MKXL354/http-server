package org.example.lifeCycle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptionHandling.ExceptionHandlingRegistry;
import org.example.io.request.HttpRequestReader;
import org.example.io.response.HttpResponseWriter;
import org.example.model.HandlerMethod;
import org.example.model.HttpHeaders;
import org.example.model.enumeration.HttpHeader;
import org.example.model.enumeration.HttpVersion;
import org.example.model.enumeration.header.HttpConnection;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;
import org.example.socket.ClientSocket;

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
        HttpRequest httpRequest = null;
        HttpResponse httpResponse = null;
        while (true) {
            try {
                httpRequest = httpRequestReader.readHttpRequest(clientSocket);
                if (httpRequest == null) {
                    break;
                }
                log.info(httpRequest.toString());
                httpResponse = new HttpResponse(null, new HttpHeaders(), null);
                httpResponse = handleHttpResponse(httpRequest, httpResponse);
                boolean keepConnectionOpen = isConnectionHeaderKeptAlive(httpRequest);
                httpResponse.getHeaders().addHeader(HttpHeader.CONNECTION,
                        keepConnectionOpen ? HttpConnection.KEEP_ALIVE.getValue() : HttpConnection.CLOSE.getValue());
                httpResponseWriter.writeHttpResponse(httpResponse, clientSocket);
                if (!keepConnectionOpen) {
                    break;
                }
            } catch (IOException e) {
                log.warn("caught socket exception, breaking request loop", e);
                break;
            } catch (Exception e) {
                HandlerMethod handlerMethod = exceptionHandlingRegistry.getHandler(e.getClass());
                if (handlerMethod == null) {
//                    TODO: handle this
//                    throw new ExceptionHandlerMethodNotFoundException()
                    log.error("could not find handler for exception: ", e);
                    break;
                }
                handlerMethod.invokeWithoutResults(e, httpRequest, httpResponse);
                try {
                    httpResponseWriter.writeHttpResponse(httpResponse, clientSocket);
                } catch (IOException ex) {
                    log.warn("caught socket exception, breaking request loop", ex);
                }
            }
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            log.warn("could not close socket", e);
        }
    }

    private boolean isConnectionHeaderKeptAlive(HttpRequest httpRequest) {
        HttpConnection connectionHeaderValue = HttpConnection.getByValue(httpRequest.getHeaders().getHeaderValue(HttpHeader.CONNECTION));
        if (connectionHeaderValue == null) {
            return !httpRequest.getRequestLine().getHttpVersion().equals(HttpVersion.HTTP1);
        } else {
            return connectionHeaderValue.equals(HttpConnection.KEEP_ALIVE);
        }
    }

    public abstract HttpResponse handleHttpResponse(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception;
}
//TODO: this class has become very messy. fix it maybe?
