package com.mahdy.httpserver.sampleclient.model;

import com.mahdy.httpserver.core.model.enumeration.HttpResponseStatus;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
public interface CustomHttpResponseStatus {

    HttpResponseStatus CUSTOM_NOT_FOUND = HttpResponseStatus.of(404, "Custom Not Found");
}
