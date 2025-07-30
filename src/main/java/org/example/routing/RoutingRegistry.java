package org.example.routing;

import org.example.model.enumeration.HttpMethod;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface RoutingRegistry {

    void register(HttpMethod method, String path, ProcessorMethod processorMethod);

    ProcessorMethod getHandler(HttpMethod method, String path);
}
