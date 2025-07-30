package org.example.routing;

import lombok.RequiredArgsConstructor;
import org.example.exception.ApplicationRuntimeException;
import org.example.model.enumeration.HttpMethod;
import org.example.routing.validation.ProcessorValidator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
@RequiredArgsConstructor
public class RoutingRegistryImpl implements RoutingRegistry {

    private final Map<String, ProcessorMethod> routeMap = new HashMap<>();

    private final ProcessorValidator processorValidator;

    @Override
    public void register(HttpMethod method, String path, ProcessorMethod processorMethod) {
        String routingKey = generateKey(method, path);
        if (routeMap.containsKey(routingKey)) {
            throw new ApplicationRuntimeException("duplicated routing processor registered for: " + routingKey);
        }
        processorValidator.checkIsValid(processorMethod);
        routeMap.put(routingKey, processorMethod);
    }

    @Override
    public ProcessorMethod getHandler(HttpMethod method, String path) {
        return routeMap.get(generateKey(method, path));
    }

    private String generateKey(HttpMethod method, String path) {
        return method.name() + " " + path;
    }
}
