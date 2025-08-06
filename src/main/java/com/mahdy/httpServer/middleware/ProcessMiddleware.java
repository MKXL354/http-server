package com.mahdy.httpServer.middleware;

import com.mahdy.httpServer.model.request.HttpRequest;
import com.mahdy.httpServer.model.response.HttpResponse;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
public interface ProcessMiddleware {

    ProcessMiddleware getNext();

    void setNext(ProcessMiddleware next);

    void process(HttpRequest request, HttpResponse response) throws Exception;
}
