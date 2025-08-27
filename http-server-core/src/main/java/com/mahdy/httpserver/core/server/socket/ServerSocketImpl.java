package com.mahdy.httpserver.core.server.socket;

import com.mahdy.httpserver.core.exception.base.ApplicationRuntimeException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@RequiredArgsConstructor
public class ServerSocketImpl implements ServerSocket {

    private final int PORT;
    private final int SOCKET_TIMEOUT_MILLIS;

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
    public int getLocalPort() {
        return PORT;
    }

    @Override
    public ClientSocket acceptConnection() throws IOException {
        Socket socket = serverSocket.accept();
        return new ClientSocketImpl(socket, SOCKET_TIMEOUT_MILLIS);
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
