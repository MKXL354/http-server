package com.mahdy.httpserver.core.routing;

import com.mahdy.httpserver.core.exception.RequestMethodNotSupportedException;
import com.mahdy.httpserver.core.exception.RequestPathNotFoundException;
import com.mahdy.httpserver.core.model.HandlerMethod;
import com.mahdy.httpserver.core.model.enumeration.HttpMethod;
import com.mahdy.httpserver.core.model.request.RequestPath;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface RoutingRegistry {

    void fillRegistry();

    HandlerMethod getHandler(HttpMethod method, RequestPath requestPath) throws RequestPathNotFoundException, RequestMethodNotSupportedException;
}
