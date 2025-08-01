package org.example.routing;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.annotation.Routing;
import org.example.model.HandlerMethod;
import org.example.model.HttpMethodPath;
import org.example.model.enumeration.HttpMethod;
import org.example.util.classpathScan.AnnotationScanner;
import org.example.validation.handler.ProcessorMethodValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
@RequiredArgsConstructor
public class RoutingRegistryImpl implements RoutingRegistry {

    private final String BASE_PACKAGE = "org.example.routing";

    private final Map<HttpMethodPath, HandlerMethod> routeMap = new HashMap<>();

    private final AnnotationScanner annotationScanner;
    private final ApplicationContext applicationContext;
    private final ProcessorMethodValidator processorMethodValidator;

    @PostConstruct
    @Override
    public void fillRegistry() {
        List<Method> methods = annotationScanner.scanForMethods(BASE_PACKAGE, Routing.class);
        for (Method method : methods) {
            Routing request = method.getAnnotation(Routing.class);
            Object instance = applicationContext.getBean(method.getDeclaringClass());
            register(request.httpMethod(), request.path(), new HandlerMethod(instance, method));
        }
    }

    @Override
    public void register(HttpMethod method, String path, HandlerMethod handlerMethod) {
        processorMethodValidator.checkIsValid(handlerMethod);
        HttpMethodPath methodPath = new HttpMethodPath(method, path);
        routeMap.put(methodPath, handlerMethod);
    }

    @Override
    public HandlerMethod getHandler(HttpMethod method, String path) {
        HttpMethodPath methodPath = new HttpMethodPath(method, path);
        return routeMap.get(methodPath);
    }

    @Override
    public boolean isPathRoutingExist(String path) {
        return routeMap.keySet().stream().anyMatch(methodPath -> methodPath.getPath().equals(path));
    }
}
