package org.example.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class ServerSocketImpl implements ServerSocket {
    private final java.net.ServerSocket serverSocket;

    public ServerSocketImpl(int port) throws IOException {
        serverSocket = new java.net.ServerSocket(port);
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
