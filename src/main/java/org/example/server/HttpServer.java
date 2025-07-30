package org.example.server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.executor.ServerLoopExecutionManager;
import org.example.executor.TaskExecutionManager;
import org.example.handler.HttpHandlerTemplate;
import org.example.socket.ClientSocket;
import org.example.socket.ServerSocket;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HttpServer {

    private final ServerLoopExecutionManager serverLoopExecutionManager;
    private final TaskExecutionManager taskExecutionManager;
    private final ServerSocket serverSocket;
    private final HttpHandlerTemplate httpHandlerTemplate;

    @PostConstruct
    public void start() {
        serverLoopExecutionManager.execute(this::serverLoop);
    }

    @PreDestroy
    public void stop() {
        try {
            taskExecutionManager.shutdown();
            serverLoopExecutionManager.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }

    private void serverLoop() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ClientSocket clientSocket = serverSocket.acceptConnection();
                taskExecutionManager.execute(() -> httpHandlerTemplate.handle(clientSocket));
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
        }
    }
}

//TODO: enhanced error handling (send error response with message/trace not just log)
//TODO: add more detailed exceptions (maybe one per logic class?)
//TODO: query param (better request line parsing) support
//TODO: util (like json?)
//TODO: test infrastructure
//TODO: config based data variables (IP, PORT, TIMEOUT, etc.) put in Beans
//TODO: @Bean (with naming to avoid dupe) instead of @Component for customization? and auto-config
//TODO: middleware chain?
