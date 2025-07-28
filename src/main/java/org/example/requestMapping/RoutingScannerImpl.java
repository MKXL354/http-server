package org.example.requestMapping;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.exception.ApplicationRuntimeException;
import org.example.requestMapping.annotation.Routing;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
@RequiredArgsConstructor
public class RoutingScannerImpl implements RoutingScanner {

    private final String BASE_PACKAGE = "org.example.controller";

    private final RoutingRegistry routingRegistry;

    @PostConstruct
    @Override
    public void scanAndRegister() {
//        TODO: maybe use better package scanning for controller registry?
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(BASE_PACKAGE).scan()) {
            for (ClassInfo classInfo : scanResult.getAllClasses()) {
                Class<?> clazz = classInfo.loadClass();
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Routing.class)) {
                        Routing request = method.getAnnotation(Routing.class);
//                        TODO: inject Spring Bean here
                        Object instance = clazz.getDeclaredConstructor().newInstance();
                        routingRegistry.register(request.httpMethod(), request.path(), new HandlerMethod(instance, method));
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationRuntimeException("Failed to scan and register request handlers", e);
        }
    }
}
