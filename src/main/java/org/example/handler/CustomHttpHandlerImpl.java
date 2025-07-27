package org.example.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ApplicationRuntimeException;
import org.example.model.request.HttpRequest;
import org.example.processor.request.HttpRequestProcessor;
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
public class CustomHttpHandlerImpl implements CustomHttpHandler {

    private final HttpRequestProcessor httpRequestProcessor;

    @Override
    public void handle(ClientSocket clientSocket) {
        try {
            HttpRequest httpRequest = httpRequestProcessor.processHttpRequest(clientSocket);
            System.out.println(httpRequest);
        } catch (ApplicationRuntimeException | IOException e) {
            log.warn(e.toString());
        }
    }
}
