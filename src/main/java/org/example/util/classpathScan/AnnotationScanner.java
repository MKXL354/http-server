package org.example.util.classpathScan;

import org.example.exception.AnnotationScannerException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
public interface AnnotationScanner {

    <T> List<Class<? extends T>> scanForType(Class<T> type, String basePackage, Class<? extends Annotation> annotationClass)
            throws AnnotationScannerException;

    List<Method> scanForMethods(String basePackage, Class<? extends Annotation> annotationClass) throws AnnotationScannerException;
}
