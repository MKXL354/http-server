package com.mahdy.httpserver.core.validation.handler;

import com.mahdy.httpserver.core.exception.base.HandlerException;
import com.mahdy.httpserver.core.model.HandlerMethod;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public interface HandlerMethodValidator {

    void checkIsValid(HandlerMethod handlerMethod) throws HandlerException;
}
