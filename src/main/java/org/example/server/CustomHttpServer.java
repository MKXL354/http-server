package org.example.server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.example.model.request.HttpRequest;
import org.example.processor.request.HttpRequestProcessor;
import org.example.socket.ClientSocket;
import org.example.socket.ServerSocket;
import org.example.socket.ServerSocketImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
@RequiredArgsConstructor
public class CustomHttpServer {

    private final int PORT = 8080;

    private ServerSocket serverSocket;
    private ClientSocket clientSocket;

    private final HttpRequestProcessor httpRequestProcessor;

    @PostConstruct
    public void start() throws IOException {
        serverSocket = new ServerSocketImpl(PORT);
        clientSocket = serverSocket.acceptConnection();
        HttpRequest httpRequest = httpRequestProcessor.processHttpRequest(clientSocket);
        System.out.println(httpRequest);
    }

    @PreDestroy
    public void stop() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}
//TODO: loop, multi-client, multi-threading
//TODO: error handling
//TODO: business based on headers
//TODO: create response and return just the request for now
//TODO: routing and business (based on http methods, path and other data)
//TODO: config based IP and PORT
//TODO: @Bean instead of @Component for customization?
