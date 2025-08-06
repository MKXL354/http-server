package com.mahdy.httpServer.routing;

import com.mahdy.httpServer.annotation.Routing;
import com.mahdy.httpServer.exception.RequestMethodNotSupportedException;
import com.mahdy.httpServer.exception.RequestPathNotFoundException;
import com.mahdy.httpServer.model.HandlerMethod;
import com.mahdy.httpServer.model.HttpMethodPath;
import com.mahdy.httpServer.model.VariableHttpMethodPath;
import com.mahdy.httpServer.model.enumeration.HttpMethod;
import com.mahdy.httpServer.model.request.RequestPath;
import com.mahdy.httpServer.util.classpathScan.AnnotationScanner;
import com.mahdy.httpServer.validation.handler.ProcessorMethodValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@RequiredArgsConstructor
public class RoutingRegistryImpl implements RoutingRegistry {

    private final List<String> basePackages;

    private final AnnotationScanner annotationScanner;
    private final ApplicationContext applicationContext;
    private final ProcessorMethodValidator processorMethodValidator;

    private final Map<HttpMethodPath, HandlerMethod> staticPaths = new HashMap<>();
    private final Map<VariableHttpMethodPath, HandlerMethod> variablePaths = new HashMap<>();

    @PostConstruct
    @Override
    public void fillRegistry() {
        List<Method> methods = annotationScanner.scanForMethods(basePackages, Routing.class);
        for (Method method : methods) {
            Routing request = method.getAnnotation(Routing.class);
            Object instance = applicationContext.getBean(method.getDeclaringClass());
            register(request.httpMethod(), request.path(), new HandlerMethod(instance, method));
        }
    }

    @Override
    public HandlerMethod getHandler(HttpMethod method, RequestPath requestPath) throws RequestPathNotFoundException,
            RequestMethodNotSupportedException {
        HandlerMethod staticPathHandler = getStaticPathHandler(method, requestPath);
        if (staticPathHandler != null) {
            return staticPathHandler;
        }
        return getVariablePathHandler(method, requestPath);
    }

    private void register(HttpMethod method, String path, HandlerMethod handlerMethod) {
        processorMethodValidator.checkIsValid(handlerMethod);
        if (path.contains("{")) {
            variablePaths.put(new VariableHttpMethodPath(method, path, path.split("/")), handlerMethod);
        } else {
            staticPaths.put(new HttpMethodPath(method, path), handlerMethod);
        }
    }

    private HandlerMethod getStaticPathHandler(HttpMethod method, RequestPath requestPath) throws RequestMethodNotSupportedException {
        Set<Map.Entry<HttpMethodPath, HandlerMethod>> matchedPaths = staticPaths.entrySet().stream()
                .filter(methodPath -> methodPath.getKey().getPath().equals(requestPath.getPathString()))
                .collect(Collectors.toSet());
        if (matchedPaths.isEmpty()) {
            return null;
        }
        for (Map.Entry<HttpMethodPath, HandlerMethod> entry : matchedPaths) {
            if (entry.getKey().getHttpMethod().equals(method)) {
                return entry.getValue();
            }
        }
        throw new RequestMethodNotSupportedException();
    }

    private HandlerMethod getVariablePathHandler(HttpMethod method, RequestPath requestPath) throws RequestPathNotFoundException,
            RequestMethodNotSupportedException {
        boolean foundPath = false;
        Map<String, String> pathVariables = new HashMap<>();
        String[] pathSegments = requestPath.getPathString().split("/");
        for (Map.Entry<VariableHttpMethodPath, HandlerMethod> entry : variablePaths.entrySet()) {
            String[] routeSegments;
            if ((routeSegments = entry.getKey().getPathSegments()).length != pathSegments.length) {
                continue;
            }
            if (matchSegments(routeSegments, pathSegments, pathVariables)) {
                foundPath = true;
                if (entry.getKey().getHttpMethod().equals(method)) {
                    requestPath.getPathVariables().putAll(pathVariables);
                    return entry.getValue();
                }
            } else {
                pathVariables.clear();
            }
        }
        if (!foundPath) {
            throw new RequestPathNotFoundException();
        }
        throw new RequestMethodNotSupportedException();
    }

    private boolean matchSegments(String[] routeSegments, String[] pathSegments, Map<String, String> pathVariables) {
        for (int i = 0; i < routeSegments.length; i++) {
            String routeSegment = routeSegments[i];
            String pathSegment = pathSegments[i];
            if (routeSegment.startsWith("{") && routeSegment.endsWith("}")) {
                String key = routeSegment.substring(1, routeSegment.length() - 1);
                pathVariables.put(key, pathSegment);
            } else if (!routeSegment.equals(pathSegment)) {
                return false;
            }
        }
        return true;
    }
}
