package com.mahdy.httpServer.io.response;

import com.mahdy.httpServer.model.response.HttpResponse;
import com.mahdy.httpServer.server.socket.ClientSocket;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface HttpResponseWriter {

    void writeHttpResponse(HttpResponse httpResponse, ClientSocket clientSocket) throws IOException;
}
