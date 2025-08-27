package com.mahdy.httpserver.core.server;

import com.mahdy.httpserver.core.executor.ServerLoopExecutionManager;
import com.mahdy.httpserver.core.executor.TaskExecutionManager;
import com.mahdy.httpserver.core.lifecycle.HttpLifeCycleTemplate;
import com.mahdy.httpserver.core.server.socket.ClientSocket;
import com.mahdy.httpserver.core.server.socket.HttpServerSocket;
import com.mahdy.httpserver.core.server.socket.HttpsServerSocket;
import com.mahdy.httpserver.core.server.socket.ServerSocket;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@RequiredArgsConstructor
@Slf4j
public class HttpServer {

    private final HttpServerProperties serverProperties;

    private final ServerLoopExecutionManager serverLoopExecutionManager;
    private final TaskExecutionManager taskExecutionManager;
    private final HttpLifeCycleTemplate httpLifeCycleTemplate;

    private ServerSocket serverSocket;

    @PostConstruct
    public void start() {
        if (serverProperties.isHttpsEnabled()) {
            log.info("https is enabled");
            serverSocket = new HttpsServerSocket(serverProperties.getPort(), serverProperties.getSocketTimeoutMillis(),
                    serverProperties.getKeyStorePath(), serverProperties.getKeyStorePassword(), serverProperties.getTlsKeyPassword());
        } else {
            serverSocket = new HttpServerSocket(serverProperties.getPort(), serverProperties.getSocketTimeoutMillis());
        }
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
        log.info("starting to accept connections on port {}...", serverProperties.getPort());
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

//TODO: an entire json modeling support as util?
//TODO: integrate middleware and processors -> non-full handler types? extension/calling another class?
//TODO: Spring style @Request annotation family? justify LifeCycleTemplate and subclasses? or strategy?
