package com.mahdy.httpserver.core.validation.handler;

import com.mahdy.httpserver.core.exception.HandlerMethodInvalidFormatException;
import com.mahdy.httpserver.core.model.HandlerMethod;
import com.mahdy.httpserver.core.model.request.HttpRequest;
import com.mahdy.httpserver.core.model.response.HttpResponse;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
@Component
public class ExceptionHandlerMethodValidatorImpl implements ExceptionHandlerMethodValidator {

    private final String METHOD_STRUCTURE = "public void <name>(Exception <param1>, HttpRequest <param2>, HttpResponse <param3>)";

    @Override
    public void checkIsValid(HandlerMethod handlerMethod) {
        if (!isValid(handlerMethod)) {
            throw new HandlerMethodInvalidFormatException("exception handler method does not follow structure " + METHOD_STRUCTURE);
        }
    }

    private boolean isValid(HandlerMethod handlerMethod) {
        Method method = handlerMethod.getMethod();
        if (!Modifier.isPublic(method.getModifiers())) return false;
        if (!method.getReturnType().equals(void.class)) return false;

        Class<?>[] params = method.getParameterTypes();
        if (params.length != 3) return false;

        return Throwable.class.isAssignableFrom(params[0]) && HttpRequest.class.isAssignableFrom(params[1]) &&
                HttpResponse.class.isAssignableFrom(params[2]);
    }
}
