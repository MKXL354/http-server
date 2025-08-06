package com.mahdy.httpServer.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Data
@Slf4j
public class HandlerMethod {

    private final Object instance;
    private final Method method;

    public Object invokeWithResults(Object... args) throws Exception {
        return method.invoke(instance, args);
    }

    public void invokeWithoutResults(Object... args) {
        try {
            method.invoke(instance, args);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
