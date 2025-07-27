package org.example.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class ServerImpl implements Server {
    private final ServerSocket serverSocket;

    public ServerImpl(int port) throws IOException {
        serverSocket = new ServerSocket(port);
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
