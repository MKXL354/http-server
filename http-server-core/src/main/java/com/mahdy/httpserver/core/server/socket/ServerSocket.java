package com.mahdy.httpserver.core.server.socket;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface ServerSocket {

    int getLocalPort();

    ClientSocket acceptConnection() throws IOException;

    void close() throws IOException;
}
