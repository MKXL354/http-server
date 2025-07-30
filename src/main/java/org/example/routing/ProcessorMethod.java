package org.example.routing;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
public class ProcessorMethod {

    private final Object instance;
    private final Method method;

    public Object invoke(Object... args) throws Exception {
        return method.invoke(instance, args);
    }
}
