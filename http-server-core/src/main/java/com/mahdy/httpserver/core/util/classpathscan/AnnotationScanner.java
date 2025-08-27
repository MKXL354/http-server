package com.mahdy.httpserver.core.util.classpathscan;

import com.mahdy.httpserver.core.exception.AnnotationScannerException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
public interface AnnotationScanner {

    <T> List<Class<? extends T>> scanForType(Class<T> type, List<String> basePackages, Class<? extends Annotation> annotationClass)
            throws AnnotationScannerException;

    List<Method> scanForMethods(List<String> basePackages, Class<? extends Annotation> annotationClass) throws AnnotationScannerException;
}
