package com.mahdy.httpserver.starter.spring.lifecycle;

import com.mahdy.httpserver.core.exception.ObjectResolverException;
import com.mahdy.httpserver.core.lifecycle.ObjectResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
@RequiredArgsConstructor
public class SpringObjectResolver implements ObjectResolver {

    private final ApplicationContext applicationContext;

    @Override
    public <T> T resolveObject(Class<T> clazz) {
        try {
            return applicationContext.getBean(clazz);
        } catch (Exception e) {
            throw new ObjectResolverException(e);
        }
    }
}
