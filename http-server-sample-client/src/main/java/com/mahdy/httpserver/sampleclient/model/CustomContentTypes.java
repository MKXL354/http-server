package com.mahdy.httpserver.sampleclient.model;

import com.mahdy.httpserver.core.model.enumeration.header.HttpContentType;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
public interface CustomContentTypes {

    HttpContentType CUSTOM_PLAIN_TEXT = HttpContentType.of("text/plain");
}
