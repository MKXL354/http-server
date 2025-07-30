package org.example.routing.validation;

import org.example.exception.ProcessorMethodInvalidFormatException;
import org.example.model.request.HttpRequest;
import org.example.model.response.HttpResponse;
import org.example.routing.ProcessorMethod;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
@Component
public class FullControlProcessorValidator implements ProcessorValidator {

    private final String PROCESSOR_METHOD_STRUCTURE = "public void <name>(HttpRequest <param1>, HttpResponse <param2>)";

    @Override
    public void checkIsValid(ProcessorMethod processorMethod) {
        if (!isValid(processorMethod)) {
            throw new ProcessorMethodInvalidFormatException("processor method does not follow structure " + PROCESSOR_METHOD_STRUCTURE);
        }
    }

    private boolean isValid(ProcessorMethod processorMethod) {
        Method method = processorMethod.getMethod();
        if (!Modifier.isPublic(method.getModifiers())) return false;
        if (!method.getReturnType().equals(void.class)) return false;

        Class<?>[] params = method.getParameterTypes();
        if (params.length != 2) return false;

        return params[0].equals(HttpRequest.class) && params[1].equals(HttpResponse.class);
    }
}
