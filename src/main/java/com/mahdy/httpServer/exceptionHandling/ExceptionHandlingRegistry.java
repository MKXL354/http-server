package com.mahdy.httpServer.exceptionHandling;

import com.mahdy.httpServer.model.HandlerMethod;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public interface ExceptionHandlingRegistry {

    void fillRegistry();

    void register(Class<? extends Throwable> exceptionClass, HandlerMethod handlerMethod);

    HandlerMethod getHandler(Class<? extends Throwable> exceptionClass);
}
