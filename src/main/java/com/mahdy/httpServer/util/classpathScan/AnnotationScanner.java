package com.mahdy.httpServer.util.classpathScan;

import com.mahdy.httpServer.exception.AnnotationScannerException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
public interface AnnotationScanner {

    <T> Set<Class<? extends T>> scanForType(Class<T> type, String basePackage, Class<? extends Annotation> annotationClass)
            throws AnnotationScannerException;

    Set<Method> scanForMethods(String basePackage, Class<? extends Annotation> annotationClass) throws AnnotationScannerException;
}
