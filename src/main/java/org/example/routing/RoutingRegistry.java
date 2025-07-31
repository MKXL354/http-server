package org.example.routing;

import org.example.model.HandlerMethod;
import org.example.model.enumeration.HttpMethod;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface RoutingRegistry {

    void fillRegistry();

    void register(HttpMethod method, String path, HandlerMethod handlerMethod);

    HandlerMethod getHandler(HttpMethod method, String path);

    boolean isPathExist(String path);
}
