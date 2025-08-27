package com.mahdy.httpserver.core.server.socket;

import com.mahdy.httpserver.core.exception.base.ApplicationRuntimeException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
@Slf4j
public class ServerSocketImpl implements ServerSocket {

    @Value("${http.server.socket.port}")
    private int PORT;
    @Value("${http.server.socket.timeout}")
    private int SOCKET_TIMEOUT;

    private java.net.ServerSocket serverSocket;

    @PostConstruct
    public void start() {
        try {
            serverSocket = new java.net.ServerSocket(PORT);
        } catch (IOException e) {
            throw new ApplicationRuntimeException(e);
        }
    }

    @Override
    public ClientSocket acceptConnection() throws IOException {
        Socket socket = serverSocket.accept();
        return new ClientSocketImpl(socket, SOCKET_TIMEOUT);
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
