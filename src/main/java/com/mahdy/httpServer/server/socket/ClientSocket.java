package com.mahdy.httpServer.server.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface ClientSocket {

    OutputStream getOutputStream() throws IOException;

    InputStream getInputStream() throws IOException;

    InetAddress getInetAddress();

    void close() throws IOException;
}
