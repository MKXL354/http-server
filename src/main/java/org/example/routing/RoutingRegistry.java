package org.example.routing;

import org.example.exception.RequestMethodNotSupportedException;
import org.example.exception.RequestPathNotFoundException;
import org.example.model.HandlerMethod;
import org.example.model.enumeration.HttpMethod;
import org.example.model.request.RequestPath;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface RoutingRegistry {

    void fillRegistry();

    HandlerMethod getHandler(HttpMethod method, RequestPath requestPath) throws RequestPathNotFoundException, RequestMethodNotSupportedException;
}
