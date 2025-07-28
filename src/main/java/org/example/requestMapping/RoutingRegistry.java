package org.example.requestMapping;

import org.example.model.enumeration.HttpMethod;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface RoutingRegistry {

    void register(HttpMethod method, String path, HandlerMethod handlerMethod);

    HandlerMethod getHandler(HttpMethod method, String path);
}
