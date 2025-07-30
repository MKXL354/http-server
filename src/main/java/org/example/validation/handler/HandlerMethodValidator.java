package org.example.validation.handler;

import org.example.exception.base.HandlerException;
import org.example.model.HandlerMethod;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public interface HandlerMethodValidator {

    void checkIsValid(HandlerMethod handlerMethod) throws HandlerException;
}
