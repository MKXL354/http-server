package com.mahdy.httpserver.core.model;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class HttpBody {

    private byte[] bodyBytes;

    public HttpBody(byte[] body) {
        this.bodyBytes = body;
    }

    public HttpBody(String body) {
        this.bodyBytes = body.getBytes();
    }

    public byte[] getBodyAsBytes() {
        return bodyBytes;
    }

    public String getBodyAsString() {
        return new String(bodyBytes);
    }

    public void setBodyAsBytes(byte[] body) {
        this.bodyBytes = body;
    }

    public void setBodyAsString(String body) {
        this.bodyBytes = body.getBytes();
    }
}
