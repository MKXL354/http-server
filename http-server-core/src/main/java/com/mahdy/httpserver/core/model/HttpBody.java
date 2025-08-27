package com.mahdy.httpserver.core.model;

import com.mahdy.httpserver.core.util.CommonUtils;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class HttpBody {

    private byte[] bodyBytes;

    private String bodyString;

    public HttpBody(byte[] body) {
        this.bodyBytes = body;
    }

    public HttpBody(String body) {
        this.bodyString = body;
        this.bodyBytes = body.getBytes();
    }

    public byte[] getBodyAsBytes() {
        return bodyBytes;
    }

    public String getBodyAsString() {
        return bodyString;
    }

    public void setBodyAsBytes(byte[] body) {
        this.bodyBytes = body;
    }

    public void setBodyAsString(String body) {
        this.bodyString = body;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("HttpBody(");
        boolean stringPresent = false;
        if (CommonUtils.isNotBlank(bodyString)) {
            stringPresent = true;
            stringBuilder.append("text=\"").append(bodyString).append("\"");
        }
        if (bodyBytes != null) {
            if (stringPresent) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("bytesLength=").append(bodyBytes.length);
        }
        return stringBuilder.append(")").toString();
    }
}
