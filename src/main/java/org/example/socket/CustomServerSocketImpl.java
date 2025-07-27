package org.example.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
@Slf4j
public class CustomServerSocketImpl implements CustomServerSocket {

    private final int PORT = 8080;

    private final ServerSocket serverSocket;

    public CustomServerSocketImpl() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
