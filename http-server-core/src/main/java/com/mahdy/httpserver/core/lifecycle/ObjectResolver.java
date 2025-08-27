package com.mahdy.httpserver.core.lifecycle;

import com.mahdy.httpserver.core.exception.ObjectResolverException;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
public interface ObjectResolver {

    <T> T resolveObject(Class<T> clazz) throws ObjectResolverException;
}
