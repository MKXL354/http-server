package com.mahdy.httpServer.annotation;

import com.mahdy.httpServer.model.enumeration.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Routing {

    HttpMethod httpMethod();

    String path();
}
