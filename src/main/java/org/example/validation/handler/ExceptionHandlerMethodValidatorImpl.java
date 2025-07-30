package org.example.validation.handler;

import org.example.exception.HandlerMethodInvalidFormatException;
import org.example.model.HandlerMethod;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
@Component
public class ExceptionHandlerMethodValidatorImpl implements ExceptionHandlerMethodValidator {

    private final String HANDLER_METHOD_STRUCTURE = "public void <name>(Exception <param1>, HttpRequest <param2>, HttpResponse <param3>)";

    @Override
    public void checkIsValid(HandlerMethod handlerMethod) {
        if (!isValid(handlerMethod)) {
            throw new HandlerMethodInvalidFormatException("exception handler method does not follow structure " + HANDLER_METHOD_STRUCTURE);
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
