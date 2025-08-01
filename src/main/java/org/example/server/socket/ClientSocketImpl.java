package org.example.server.socket;

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

    private final int SOCKET_TIMEOUT = 15000;

    private final Socket clientSocket;

    public ClientSocketImpl(Socket clientSocket) throws SocketException {
        this.clientSocket = clientSocket;
        clientSocket.setSoTimeout(SOCKET_TIMEOUT);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return clientSocket.getOutputStream();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        System.out.println(clientSocket.getInetAddress().getHostAddress());
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
