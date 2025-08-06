package com.mahdy.httpServer.validation.handler;

import com.mahdy.httpServer.exception.HandlerMethodInvalidFormatException;
import com.mahdy.httpServer.model.HandlerMethod;
import com.mahdy.httpServer.model.request.HttpRequest;
import com.mahdy.httpServer.model.response.HttpResponse;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
@Component
public class FullControlProcessorMethodValidator implements ProcessorMethodValidator {

    private final String PROCESSOR_METHOD_STRUCTURE = "public void <name>(HttpRequest <param1>, HttpResponse <param2>)";

    @Override
    public void checkIsValid(HandlerMethod handlerMethod) {
        if (!isValid(handlerMethod)) {
            throw new HandlerMethodInvalidFormatException("processor method does not follow structure " + PROCESSOR_METHOD_STRUCTURE);
        }
    }

    private boolean isValid(HandlerMethod handlerMethod) {
        Method method = handlerMethod.getMethod();
        if (!Modifier.isPublic(method.getModifiers())) return false;
        if (!method.getReturnType().equals(void.class)) return false;

        Class<?>[] params = method.getParameterTypes();
        if (params.length != 2) return false;

        return HttpRequest.class.isAssignableFrom(params[0]) && HttpResponse.class.isAssignableFrom(params[1]);
    }
}
