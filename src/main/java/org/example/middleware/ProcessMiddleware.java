package org.example.middleware;

import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
public interface ProcessMiddleware {

    ProcessMiddleware getNext();

    void setNext(ProcessMiddleware next);

    void process(HttpRequest request, HttpResponse response) throws Exception;
}
