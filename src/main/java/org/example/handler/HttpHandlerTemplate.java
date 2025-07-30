package org.example.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ApplicationRuntimeException;
import org.example.io.request.HttpRequestReader;
import org.example.io.response.HttpResponseWriter;
import org.example.model.enumeration.HttpHeader;
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
public abstract class HttpHandlerTemplate {

    private final HttpRequestReader httpRequestReader;
    private final HttpResponseWriter httpResponseWriter;

    public void handle(ClientSocket clientSocket) {
        try {
            HttpRequest httpRequest = httpRequestReader.readHttpRequest(clientSocket);
            log.info(httpRequest.toString());
            HttpResponse httpResponse = getHttpResponse(httpRequest);
            boolean keepConnectionOpen = isConnectionOpen(httpRequest);
            if (keepConnectionOpen) {
                httpResponse.getHeaders().addHeader(HttpHeader.CONNECTION, HttpConnection.KEEP_ALIVE.getValue());
            }
            httpResponseWriter.writeHttpResponse(httpResponse, clientSocket);
            if (!keepConnectionOpen) {
                clientSocket.close();
            }
        } catch (ApplicationRuntimeException | IOException e) {
//            TODO: better management of ApplicationRuntimeException?
            log.warn(e.getMessage(), e);
        } catch (Exception e) {
//            TODO: global exception handling
            log.warn(e.getMessage(), e);
        }
    }

    private boolean isConnectionOpen(HttpRequest httpRequest) {
        HttpConnection connectionHeaderValue = HttpConnection.getByValue(httpRequest.getHeaders().getHeaderValue(HttpHeader.CONNECTION));
        return connectionHeaderValue == null || connectionHeaderValue.equals(HttpConnection.KEEP_ALIVE);
    }

    public abstract HttpResponse getHttpResponse(HttpRequest httpRequest) throws Exception;
}
