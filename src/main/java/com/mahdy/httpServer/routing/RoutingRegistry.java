package com.mahdy.httpServer.routing;

import com.mahdy.httpServer.exception.RequestMethodNotSupportedException;
import com.mahdy.httpServer.exception.RequestPathNotFoundException;
import com.mahdy.httpServer.model.HandlerMethod;
import com.mahdy.httpServer.model.enumeration.HttpMethod;
import com.mahdy.httpServer.model.request.RequestPath;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface RoutingRegistry {

    void fillRegistry();

    HandlerMethod getHandler(HttpMethod method, RequestPath requestPath) throws RequestPathNotFoundException, RequestMethodNotSupportedException;
}
