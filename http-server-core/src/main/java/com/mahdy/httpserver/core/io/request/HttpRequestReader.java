package com.mahdy.httpserver.core.io.request;

import com.mahdy.httpserver.core.exception.MalformedHttpRequestException;
import com.mahdy.httpserver.core.model.request.HttpRequest;
import com.mahdy.httpserver.core.server.socket.ClientSocket;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface HttpRequestReader {

    HttpRequest readHttpRequest(ClientSocket clientSocket) throws MalformedHttpRequestException, IOException;
}
