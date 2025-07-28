package org.example.handler;

import org.example.socket.ClientSocket;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface HttpHandler {

    void handle(ClientSocket clientSocket);
}
