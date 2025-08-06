package com.mahdy.httpServer.io.request;

import com.mahdy.httpServer.exception.MalformedHttpRequestException;
import com.mahdy.httpServer.model.request.HttpRequest;
import com.mahdy.httpServer.server.socket.ClientSocket;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface HttpRequestReader {

    HttpRequest readHttpRequest(ClientSocket clientSocket) throws MalformedHttpRequestException, IOException;
}
