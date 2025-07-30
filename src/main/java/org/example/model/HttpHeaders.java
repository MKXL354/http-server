package org.example.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.model.enumeration.HttpHeader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
public class HttpHeaders {

    @Getter
    @Setter(AccessLevel.NONE)
    private Map<HttpHeader, String> headerMap = new HashMap<>();

    public void addHeader(HttpHeader header, String value) {
        headerMap.put(header, value);
    }

    public String getHeaderValue(HttpHeader header) {
        return headerMap.get(header);
    }
}
