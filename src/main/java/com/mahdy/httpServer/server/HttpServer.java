package com.mahdy.httpServer.server;

import com.mahdy.httpServer.executor.ServerLoopExecutionManager;
import com.mahdy.httpServer.executor.TaskExecutionManager;
import com.mahdy.httpServer.server.lifeCycle.HttpLifeCycleTemplate;
import com.mahdy.httpServer.server.socket.ClientSocket;
import com.mahdy.httpServer.server.socket.ServerSocket;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

//TODO: MalformedHttpRequestException informative messages?
//TODO: config based data variables (IP, PORT, TIMEOUT, etc.) put in Beans
//TODO: examples in SimpleFullControlProcessor for features
//TODO: TLS support
//TODO: make changes so that exception handler does not need to write so many things?
//TODO: util (like json modeling, static resource resolution? and non-blocking file read/write?)
//TODO: integrate middleware and processors -> non-full handler types? extension/calling another class?
//TODO: Spring style @Request annotation family? justify LifeCycleTemplate and subclasses? or strategy?
