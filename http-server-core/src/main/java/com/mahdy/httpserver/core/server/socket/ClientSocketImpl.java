package com.mahdy.httpserver.core.server.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class ClientSocketImpl implements ClientSocket {

    private final Socket clientSocket;

    public ClientSocketImpl(Socket clientSocket, int socketTimeout) throws SocketException {
        this.clientSocket = clientSocket;
        this.clientSocket.setSoTimeout(socketTimeout);
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
    public InetAddress getInetAddress() {
        return clientSocket.getInetAddress();
    }

    @Override
    public void close() throws IOException {
        clientSocket.close();
    }
}
