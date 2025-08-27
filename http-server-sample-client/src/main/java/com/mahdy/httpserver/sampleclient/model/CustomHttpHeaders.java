package com.mahdy.httpserver.sampleclient.model;

import com.mahdy.httpserver.core.model.enumeration.HttpHeader;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
public interface CustomHttpHeaders {

    HttpHeader CUSTOM_KEY = HttpHeader.of("Custom-Key");
}
