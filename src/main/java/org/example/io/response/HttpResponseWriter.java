package org.example.io.response;

import org.example.model.response.HttpResponse;
import org.example.server.ClientSocket;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface HttpResponseWriter {

    void writeHttpResponse(HttpResponse httpResponse, ClientSocket clientSocket) throws IOException;
}
