package org.example.handler;

import org.example.socket.ClientSocket;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface CustomHttpHandler {

    void handle(ClientSocket clientSocket);
}
