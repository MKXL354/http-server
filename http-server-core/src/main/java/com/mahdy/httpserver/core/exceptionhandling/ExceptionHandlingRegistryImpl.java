package com.mahdy.httpserver.core.exceptionhandling;

import com.mahdy.httpserver.core.annotation.ExceptionHandling;
import com.mahdy.httpserver.core.model.HandlerMethod;
import com.mahdy.httpserver.core.util.classpathscan.AnnotationScanner;
import com.mahdy.httpserver.core.validation.handler.ExceptionHandlerMethodValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
@RequiredArgsConstructor
public class ExceptionHandlingRegistryImpl implements ExceptionHandlingRegistry {

    private final List<String> basePackages;

    private final AnnotationScanner annotationScanner;
    private final ApplicationContext applicationContext;
    private final ExceptionHandlerMethodValidator exceptionHandlerMethodValidator;

    private final Map<Class<? extends Throwable>, HandlerMethod> exceptionHandlers = new HashMap<>();

    @PostConstruct
    @Override
    public void fillRegistry() {
        List<Method> methods = annotationScanner.scanForMethods(basePackages, ExceptionHandling.class);
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
