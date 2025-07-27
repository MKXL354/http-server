package org.example.processor.request;

import org.example.model.HttpBody;
import org.example.model.HttpHeader;
import org.example.model.HttpHeaders;
import org.example.model.HttpVersion;
import org.example.model.request.HttpMethod;
import org.example.model.request.HttpRequest;
import org.example.model.request.RequestLine;
import org.example.model.request.RequestPath;
import org.example.socket.ClientSocket;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class HttpRequestProcessorImpl implements HttpRequestProcessor {

    @Override
    public HttpRequest processHttpRequest(ClientSocket clientSocket) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        RequestLine requestLine = processRequestLine(input);
        HttpHeaders headers = processHttpHeaders(input);
        HttpBody body = processHttpBody(headers, input);
        return new HttpRequest(requestLine, headers, body);
    }

    private RequestLine processRequestLine(BufferedReader input) throws IOException {
        String line = input.readLine();
        String[] sections = line.split(" ");
        HttpMethod method = HttpMethod.valueOf(sections[0]);
        RequestPath path = new RequestPath(sections[1]);
        HttpVersion version = HttpVersion.getByValue(sections[2]);
        return new RequestLine(method, path, version);
    }

    private HttpHeaders processHttpHeaders(BufferedReader input) throws IOException {
        String line;
        Map<HttpHeader, String> headers = new HashMap<>();
        while ((line = input.readLine()) != null && !line.isEmpty()) {
            int colonIndex = line.indexOf(":");
            if (colonIndex > 0) {
                HttpHeader key = HttpHeader.getByValue(line.substring(0, colonIndex).trim());
                String value = line.substring(colonIndex + 1).trim();
                headers.put(key, value);
            }
        }
        return new HttpHeaders(headers);
    }

    private HttpBody processHttpBody(HttpHeaders headers, BufferedReader input) throws IOException {
//        TODO: read body in special ways (abstraction)? pass mode based on headers
        String contentLengthValue = headers.getHeaderValue(HttpHeader.CONTENT_LENGTH);
        if (contentLengthValue != null) {
            int contentLength = Integer.parseInt(contentLengthValue);
            char[] buffer = new char[contentLength];
            int read = input.read(buffer, 0, contentLength);
            String body = new String(buffer, 0, read);
            return new HttpBody(body);
        }
        return null;
    }
}
