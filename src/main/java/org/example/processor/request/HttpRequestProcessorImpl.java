package org.example.processor.request;

import org.example.exception.MalformedHttpRequestException;
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
import org.springframework.util.StringUtils;

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

    private final int MAX_CONTENT_LENGTH = 65536;

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
        if (!StringUtils.hasText(line)) {
            throw new MalformedHttpRequestException();
        }
        String[] sections = line.split(" ");
        if (sections.length != 3) {
            throw new MalformedHttpRequestException();
        }
        HttpMethod method = HttpMethod.getByValue(sections[0]);
        if (method == null) {
            throw new MalformedHttpRequestException();
        }
        RequestPath path = new RequestPath(sections[1]);
        HttpVersion version = HttpVersion.getByValue(sections[2]);
        if (version == null) {
            throw new MalformedHttpRequestException();
        }
        return new RequestLine(method, path, version);
    }

    private HttpHeaders processHttpHeaders(BufferedReader input) throws IOException {
        String line;
        Map<HttpHeader, String> headers = new HashMap<>();
        while ((line = input.readLine()) != null && !line.isBlank()) {
            int colonIndex = line.indexOf(":");
            if (colonIndex == -1) {
                throw new MalformedHttpRequestException();
            }
            HttpHeader key = HttpHeader.getByValue(line.substring(0, colonIndex).trim());
            if (key == null || headers.containsKey(key)) {
                throw new MalformedHttpRequestException();
            }
            String value = line.substring(colonIndex + 1).trim();
            if (value.isEmpty()) {
                throw new MalformedHttpRequestException();
            }
            headers.put(key, value);
        }
        return new HttpHeaders(headers);
    }

    private HttpBody processHttpBody(HttpHeaders headers, BufferedReader input) throws IOException {
        String contentLengthValue = headers.getHeaderValue(HttpHeader.CONTENT_LENGTH);
        if (contentLengthValue != null) {
            try {
                int contentLength = Integer.parseInt(contentLengthValue);
                if (contentLength > MAX_CONTENT_LENGTH) {
                    throw new MalformedHttpRequestException();
                }
                char[] buffer = new char[contentLength];
                int read = input.read(buffer, 0, contentLength);
                String body = new String(buffer, 0, read);
                return new HttpBody(body.trim());
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw new MalformedHttpRequestException(e);
            }
        }
        return null;
    }
}
