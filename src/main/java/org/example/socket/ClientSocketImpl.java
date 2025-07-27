package org.example.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class ClientSocketImpl implements ClientSocket {

    private final Socket clientSocket;

    public ClientSocketImpl(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return clientSocket.getOutputStream();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return clientSocket.getInputStream();
    }

    @Override
    public void close() throws IOException {
        clientSocket.close();
    }
}
