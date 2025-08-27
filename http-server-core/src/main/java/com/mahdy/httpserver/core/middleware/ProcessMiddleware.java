package com.mahdy.httpserver.core.middleware;

import com.mahdy.httpserver.core.model.request.HttpRequest;
import com.mahdy.httpserver.core.model.response.HttpResponse;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
public interface ProcessMiddleware {

    ProcessMiddleware getNext();

    void setNext(ProcessMiddleware next);

    void process(HttpRequest request, HttpResponse response) throws Exception;
}
