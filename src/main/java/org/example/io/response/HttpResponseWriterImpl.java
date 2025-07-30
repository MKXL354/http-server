package org.example.io.response;

import lombok.extern.slf4j.Slf4j;
import org.example.model.HttpBody;
import org.example.model.enumeration.HttpHeader;
import org.example.model.response.HttpResponse;
import org.example.socket.ClientSocket;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

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

        HttpBody body = response.getBody();
        if (body != null && StringUtils.hasText(body.getBodyString())) {
            String bodyString = body.getBodyString();
            response.getHeaders().addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(bodyString.getBytes(StandardCharsets.UTF_8).length));
            response.getHeaders().getHeaderMap().forEach((key, value) ->
                    responseToWrite.append(key.getValue()).append(HEADER_SEPARATOR).append(value).append(LINE_SEPARATOR));
            responseToWrite.append(LINE_SEPARATOR);
            responseToWrite.append(body.getBodyString());
        }

        log.info(response.toString());
        writer.write(responseToWrite.toString());
        writer.flush();
    }
}
