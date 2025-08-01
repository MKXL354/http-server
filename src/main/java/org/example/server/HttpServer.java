package org.example.server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.executor.ServerLoopExecutionManager;
import org.example.executor.TaskExecutionManager;
import org.example.lifeCycle.HttpLifeCycleTemplate;
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
    private final HttpLifeCycleTemplate httpLifeCycleTemplate;

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
            log.warn("could not shut down executors and close server", e);
        }
    }

    private void serverLoop() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ClientSocket clientSocket = serverSocket.acceptConnection();
                log.info("client socket opened: {}", clientSocket);
                taskExecutionManager.execute(() -> httpLifeCycleTemplate.executeLifeCycle(clientSocket));
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
        }
    }
}

//TODO: context in request object, fill with data like ip, time and connection header status?
//TODO: give dev ability to fill context -> map? special object? generic?
//TODO: integrate middleware and processors -> non-full handler types? extension/calling another class?
//TODO: query param (better request line parsing) support
//TODO: util (like json modeling, static resource resolution and non-blocking file read/write)
//TODO: static binary resources handling? binary/string body in response? write/set only one
//TODO: config based data variables (IP, PORT, TIMEOUT, etc.) put in Beans
//TODO: maybe remove some of the redundant interfaces and replace with their single possible Impl?
//TODO: remove redundant packages
//TODO: add multi-package searching to registries to allow for default and dev-defined packages
//TODO: @Bean (with naming to avoid dupe) instead of @Component for customization? and auto-config
