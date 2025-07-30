package org.example.io.response;

import org.example.model.HttpBody;
import org.example.model.enumeration.HttpHeader;
import org.example.model.enumeration.header.HttpConnection;
import org.example.model.enumeration.header.HttpContentType;
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
public class HttpResponseWriterImpl implements HttpResponseWriter {

    private final String LINE_SEPARATOR = "\r\n";
    private final String HEADER_SEPARATOR = ": ";

    @Override
    public void writeHttpResponse(HttpResponse response, ClientSocket clientSocket) throws IOException {
//        TODO: is error handling needed?
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        StringBuilder responseToWrite = new StringBuilder();
        String statusLineString = String.format("%s %d %s",
                response.getStatusLine().getHttpVersion().getLabel(),
                response.getStatusLine().getHttpResponseStatus().getCode(),
                response.getStatusLine().getHttpResponseStatus().getReasonPhrase());
        responseToWrite.append(statusLineString).append(LINE_SEPARATOR);

//        TODO: better handling of header and body codependence?
        HttpBody body = response.getBody();
        String headers = getHttpHeaders(body);
        responseToWrite.append(headers).append(LINE_SEPARATOR);

        if (body != null && StringUtils.hasText(body.getBody())) {
            responseToWrite.append(body.getBody());
        }
        writer.write(responseToWrite.toString());
        writer.flush();
    }

    private String getHttpHeaders(HttpBody body) {
        StringBuilder headersString = new StringBuilder();
        headersString.append(HttpHeader.CONNECTION.getValue()).append(HEADER_SEPARATOR).append(HttpConnection.CLOSE.getValue()).append(LINE_SEPARATOR);
        if (body != null && StringUtils.hasText(body.getBody())) {
            String bodyString = body.getBody();
            headersString.append(HttpHeader.CONTENT_TYPE.getValue()).append(HEADER_SEPARATOR).append(HttpContentType.PLAIN_TEXT.getValue()).append(LINE_SEPARATOR);
//            TODO: check for non-ASCII
            headersString.append(HttpHeader.CONTENT_LENGTH.getValue()).append(HEADER_SEPARATOR).append(bodyString.getBytes(StandardCharsets.UTF_8).length).append(LINE_SEPARATOR);
        }
        return headersString.toString();
    }
}
