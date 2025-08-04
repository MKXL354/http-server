package org.example.exceptionHandling;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.annotation.ExceptionHandling;
import org.example.model.HandlerMethod;
import org.example.util.classpathScan.AnnotationScanner;
import org.example.validation.handler.ExceptionHandlerMethodValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
@Component
@RequiredArgsConstructor
public class ExceptionHandlingRegistryImpl implements ExceptionHandlingRegistry {

    private final String BASE_PACKAGE = "org.example.exceptionHandling";

    private final Map<Class<? extends Throwable>, HandlerMethod> exceptionHandlers = new HashMap<>();

    private final AnnotationScanner annotationScanner;
    private final ApplicationContext applicationContext;
    private final ExceptionHandlerMethodValidator exceptionHandlerMethodValidator;

    @PostConstruct
    @Override
    public void fillRegistry() {
        Set<Method> methods = annotationScanner.scanForMethods(BASE_PACKAGE, ExceptionHandling.class);
        for (Method method : methods) {
            ExceptionHandling request = method.getAnnotation(ExceptionHandling.class);
            Object instance = applicationContext.getBean(method.getDeclaringClass());
            register(request.value(), new HandlerMethod(instance, method));
        }
    }

    @Override
    public void register(Class<? extends Throwable> exceptionClass, HandlerMethod handlerMethod) {
        exceptionHandlerMethodValidator.checkIsValid(handlerMethod);
        exceptionHandlers.put(exceptionClass, handlerMethod);
    }

    @Override
    public HandlerMethod getHandler(Class<? extends Throwable> exceptionClass) {
        Class<?> current = exceptionClass;
        HandlerMethod handler;
        while (current != null) {
            handler = exceptionHandlers.get(current);
            if (handler != null) {
                if (!exceptionHandlers.containsKey(exceptionClass)) {
                    exceptionHandlers.put(exceptionClass, handler);
                }
                return handler;
            }
            current = current.getSuperclass();
        }
        return null;
    }
}
