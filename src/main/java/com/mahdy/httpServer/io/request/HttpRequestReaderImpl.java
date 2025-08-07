package com.mahdy.httpServer.io.request;

import com.mahdy.httpServer.exception.MalformedHttpRequestException;
import com.mahdy.httpServer.model.HttpBody;
import com.mahdy.httpServer.model.HttpContext;
import com.mahdy.httpServer.model.HttpHeaders;
import com.mahdy.httpServer.model.enumeration.HttpHeader;
import com.mahdy.httpServer.model.enumeration.HttpMethod;
import com.mahdy.httpServer.model.enumeration.HttpVersion;
import com.mahdy.httpServer.model.request.HttpRequest;
import com.mahdy.httpServer.model.request.RequestLine;
import com.mahdy.httpServer.model.request.RequestPath;
import com.mahdy.httpServer.server.socket.ClientSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
@Slf4j
public class HttpRequestReaderImpl implements HttpRequestReader {

    private final int MAX_CONTENT_LENGTH = 65536;

    @Override
    public HttpRequest readHttpRequest(ClientSocket clientSocket) throws MalformedHttpRequestException, IOException {
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        RequestLine requestLine = readRequestLine(input);
        if (requestLine == null) {
            return null;
        }
        HttpHeaders headers = readHttpHeaders(input);
        HttpBody body = readHttpBody(headers, inputStream);
        HttpContext httpContext = new HttpContext();
        httpContext.setClientIp(clientSocket.getInetAddress().getHostAddress());
        return new HttpRequest(requestLine, headers, body, httpContext);
    }

    private RequestLine readRequestLine(BufferedReader inputReader) throws MalformedHttpRequestException, IOException {
        String line = inputReader.readLine();
        if (line == null) {
            log.info("EOF reached");
            return null;
        }
        if (!StringUtils.hasText(line)) {
            throw new MalformedHttpRequestException("request line should not be empty");
        }
        String[] sections = line.split(" ");
        if (sections.length != 3) {
            throw new MalformedHttpRequestException("request line should contain three sections");
        }
        HttpMethod method = HttpMethod.getByValue(sections[0]);
        if (method == null) {
            throw new MalformedHttpRequestException("http method " + sections[0] + " not recognized");
        }
        RequestPath path = readRequestPath(sections[1]);
        HttpVersion version = HttpVersion.getByLabel(sections[2]);
        if (version == null) {
            throw new MalformedHttpRequestException("http version " + sections[2] + " not recognized");
        }
        return new RequestLine(method, path, version);
    }

    private RequestPath readRequestPath(String line) throws MalformedHttpRequestException {
        String[] sections = line.split("\\?", 2);
        RequestPath requestPath = new RequestPath(sections[0]);
        if (sections.length == 2) {
            for (String param : sections[1].split("&")) {
                String[] keyValuePair = param.split("=", 2);
                if (keyValuePair.length != 2) {
                    throw new MalformedHttpRequestException("query parameter " + keyValuePair[0] + " not formatted correctly");
                }
                requestPath.getQueryParameters().put(keyValuePair[0], keyValuePair[1]);
            }
        }
        return requestPath;
    }

    private HttpHeaders readHttpHeaders(BufferedReader inputReader) throws MalformedHttpRequestException, IOException {
        String line;
        HttpHeaders headers = new HttpHeaders();
        Map<HttpHeader, String> headerMap = headers.getHeaderMap();
        while ((line = inputReader.readLine()) != null && !line.isBlank()) {
            int colonIndex = line.indexOf(":");
            if (colonIndex == -1) {
                throw new MalformedHttpRequestException("header " + line + " not formatted correctly");
            }
            HttpHeader key = HttpHeader.getByValue(line.substring(0, colonIndex).trim());
            if (key == null) {
                continue;
            }
            if (headerMap.get(key) != null) {
                throw new MalformedHttpRequestException("header " + key + " has duplicate values");
            }
            String value = line.substring(colonIndex + 1).trim();
            if (value.isEmpty()) {
                throw new MalformedHttpRequestException("header " + key + " has no value");
            }
            headerMap.put(key, value);
        }
        return headers;
    }

    private HttpBody readHttpBody(HttpHeaders headers, InputStream inputReader) throws MalformedHttpRequestException, IOException {
        String contentLengthValue = headers.getHeaderMap().get(HttpHeader.CONTENT_LENGTH);
        if (contentLengthValue != null) {
            try {
                int contentLength = Integer.parseInt(contentLengthValue);
                if (contentLength > MAX_CONTENT_LENGTH) {
                    throw new MalformedHttpRequestException("content length " + contentLengthValue + " exceeds maximum " + MAX_CONTENT_LENGTH);
                }
                byte[] buffer = new byte[contentLength];
                int bytesRead = 0;
                while (bytesRead < contentLength) {
                    int actualRead = inputReader.read(buffer, bytesRead, contentLength - bytesRead);
                    if (actualRead == -1) break;
                    bytesRead += actualRead;
                }
                if (bytesRead != contentLength) {
                    throw new MalformedHttpRequestException("actual bytes read " + bytesRead +
                            " is not equal to expected content length " + contentLength);
                }
                return new HttpBody(buffer);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw new MalformedHttpRequestException(contentLengthValue + " has invalid value");
            }
        }
        return null;
    }
}
