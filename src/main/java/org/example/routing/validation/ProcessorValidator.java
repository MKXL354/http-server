package org.example.routing.validation;

import org.example.routing.ProcessorMethod;

/**
 * @author Mehdi Kamali
 * @since 30/07/2025
 */
public interface ProcessorValidator {

    void checkIsValid(ProcessorMethod processorMethod);
}
