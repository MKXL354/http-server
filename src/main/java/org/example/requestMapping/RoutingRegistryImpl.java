package org.example.requestMapping;

import org.example.model.enumeration.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class RoutingRegistryImpl implements RoutingRegistry {

    private final Map<String, HandlerMethod> routeMap = new HashMap<>();

    @Override
    public void register(HttpMethod method, String path, HandlerMethod handlerMethod) {
        routeMap.put(generateKey(method, path), handlerMethod);
    }

    @Override
    public HandlerMethod getHandler(HttpMethod method, String path) {
        return routeMap.get(generateKey(method, path));
    }

    private String generateKey(HttpMethod method, String path) {
//        TODO: better request path matching?
        return method.name() + " " + path;
    }
}
