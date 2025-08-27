package com.mahdy.httpserver.core.exceptionhandling;

import com.mahdy.httpserver.core.model.HandlerMethod;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public interface ExceptionHandlingRegistry {

    void fillRegistry();

    void register(Class<? extends Throwable> exceptionClass, HandlerMethod handlerMethod);

    HandlerMethod getHandler(Class<? extends Throwable> exceptionClass);
}
