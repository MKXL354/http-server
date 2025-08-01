package org.example.io.response;

import lombok.extern.slf4j.Slf4j;
import org.example.model.response.HttpResponse;
import org.example.server.socket.ClientSocket;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
@Slf4j
public class HttpResponseWriterImpl implements HttpResponseWriter {

    private final String LINE_SEPARATOR = "\r\n";
    private final String HEADER_SEPARATOR = ": ";

    @Override
    public void writeHttpResponse(HttpResponse response, ClientSocket clientSocket) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        StringBuilder responseToWrite = new StringBuilder();
        String statusLineString = String.format("%s %d %s",
                response.getStatusLine().getHttpVersion().getLabel(),
                response.getStatusLine().getHttpResponseStatus().getCode(),
                response.getStatusLine().getHttpResponseStatus().getReasonPhrase());
        responseToWrite.append(statusLineString).append(LINE_SEPARATOR);

        response.getHeaders().getHeaderMap().forEach((key, value) ->
                responseToWrite.append(key.getValue()).append(HEADER_SEPARATOR).append(value).append(LINE_SEPARATOR));
        responseToWrite.append(LINE_SEPARATOR);
        responseToWrite.append(response.getBody().getBodyString());

        writer.write(responseToWrite.toString());
        writer.flush();
    }
}
