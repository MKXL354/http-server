package org.example.server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.executor.ServerLoopExecutionManager;
import org.example.executor.TaskExecutionManager;
import org.example.handler.CustomHttpHandler;
import org.example.socket.ClientSocket;
import org.example.socket.CustomServerSocket;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomHttpServer {

    private final ServerLoopExecutionManager serverLoopExecutionManager;
    private final TaskExecutionManager taskExecutionManager;
    private final CustomServerSocket customServerSocket;
    private final CustomHttpHandler customHttpHandler;

    @PostConstruct
    public void start() {
        serverLoopExecutionManager.execute(this::serverLoop);
    }

    @PreDestroy
    public void stop() {
        try {
            taskExecutionManager.shutdown();
            serverLoopExecutionManager.shutdown();
            customServerSocket.close();
        } catch (IOException e) {
            log.warn(e.toString());
        }
    }

    private void serverLoop() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ClientSocket clientSocket = customServerSocket.acceptConnection();
                taskExecutionManager.execute(() -> customHttpHandler.handle(clientSocket));
            } catch (IOException e) {
                log.warn(e.toString());
            }
        }
    }
}
//TODO: create response and return just the request for now
//TODO: test infrastructure
//TODO: enhanced error handling (send error response with message/trace not just log)
//TODO: meaningful messages read from config encapsulated in exceptions?
//TODO: server business (read body (length, chunked, json), block ip, etc.) based on headers
//TODO: routing based on http methods, path and other data
//TODO: config based IP and PORT
//TODO: @Bean instead of @Component for customization? and auto-config
