package com.mahdy.httpServer.util.classpathScan;

import com.mahdy.httpServer.exception.AnnotationScannerException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Mehdi Kamali
 * @since 06/08/2025
 */
public class SpringAnnotationScanner implements AnnotationScanner {

    @Override
    public <T> Set<Class<? extends T>> scanForType(Class<T> type, String basePackage, Class<? extends Annotation> annotationClass) throws AnnotationScannerException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(annotationClass));
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);

        Set<Class<? extends T>> result = new HashSet<>();
        for (BeanDefinition bean : candidateComponents) {
            try {
                Class<?> clazz = Class.forName(bean.getBeanClassName());
                if (type.isAssignableFrom(clazz)) {
                    result.add(clazz.asSubclass(type));
                }
            } catch (ClassNotFoundException e) {
                throw new AnnotationScannerException("Could not load class: " + bean.getBeanClassName(), e);
            }
        }
        return result;
    }

    @Override
    public Set<Method> scanForMethods(String basePackage, Class<? extends Annotation> annotationClass) throws AnnotationScannerException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

        Set<Method> methods = new HashSet<>();
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
        for (BeanDefinition bean : candidateComponents) {
            try {
                Class<?> clazz = Class.forName(bean.getBeanClassName());
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(annotationClass)) {
                        methods.add(method);
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new AnnotationScannerException("Could not load class: " + bean.getBeanClassName(), e);
            }
        }

        return methods;
    }
}
