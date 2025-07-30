package org.example.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.io.request.HttpRequestReader;
import org.example.io.response.HttpResponseWriter;
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
public abstract class HttpHandlerTemplate {

    private final HttpRequestReader httpRequestReader;
    private final HttpResponseWriter httpResponseWriter;

    public void handle(ClientSocket clientSocket) {
        while (true) {
            try {
                HttpRequest httpRequest = httpRequestReader.readHttpRequest(clientSocket);
                if (httpRequest == null) {
                    break;
                }
                log.info(httpRequest.toString());
                HttpResponse httpResponse = getHttpResponse(httpRequest);
                boolean keepConnectionOpen = isConnectionHeaderKeptAlive(httpRequest);
                if (keepConnectionOpen) {
                    httpResponse.getHeaders().addHeader(HttpHeader.CONNECTION, HttpConnection.KEEP_ALIVE.getValue());
                } else {
                    httpResponse.getHeaders().addHeader(HttpHeader.CONNECTION, HttpConnection.CLOSE.getValue());
                }
                httpResponseWriter.writeHttpResponse(httpResponse, clientSocket);
                if (!keepConnectionOpen) {
                    break;
                }
            } catch (IOException e) {
                log.warn("caught socket exception, breaking loop read", e);
                break;
            } catch (Exception e) {
//            TODO: global exception handling
                log.warn(e.getMessage(), e);
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

    public abstract HttpResponse getHttpResponse(HttpRequest httpRequest) throws Exception;
}
