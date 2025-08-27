package com.mahdy.httpserver.core.io.response;

import com.mahdy.httpserver.core.model.response.HttpResponse;
import com.mahdy.httpserver.core.server.socket.ClientSocket;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface HttpResponseWriter {

    void writeHttpResponse(HttpResponse httpResponse, ClientSocket clientSocket) throws IOException;
}
