package org.example.processor.request;

import org.example.model.request.HttpRequest;
import org.example.socket.ClientSocket;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface HttpRequestProcessor {

    HttpRequest processHttpRequest(ClientSocket clientSocket) throws IOException;
}
