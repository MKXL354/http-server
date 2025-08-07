package com.mahdy.httpServer.server.socket;

import com.mahdy.httpServer.exception.base.ApplicationRuntimeException;
import lombok.extern.slf4j.Slf4j;
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

    private final int PORT = 8080;

    private final java.net.ServerSocket serverSocket;

    public ServerSocketImpl() {
        try {
            serverSocket = new java.net.ServerSocket(PORT);
        } catch (IOException e) {
            throw new ApplicationRuntimeException(e);
        }
    }

    @Override
    public ClientSocket acceptConnection() throws IOException {
        Socket socket = serverSocket.accept();
        return new ClientSocketImpl(socket);
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
