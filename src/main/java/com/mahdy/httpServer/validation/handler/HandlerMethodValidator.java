package com.mahdy.httpServer.validation.handler;

import com.mahdy.httpServer.exception.base.HandlerException;
import com.mahdy.httpServer.model.HandlerMethod;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public interface HandlerMethodValidator {

    void checkIsValid(HandlerMethod handlerMethod) throws HandlerException;
}
