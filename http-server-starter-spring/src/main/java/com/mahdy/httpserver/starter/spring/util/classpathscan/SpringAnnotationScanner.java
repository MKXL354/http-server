package com.mahdy.httpserver.starter.spring.util.classpathscan;

import com.mahdy.httpserver.core.exception.AnnotationScannerException;
import com.mahdy.httpserver.core.util.classpathscan.AnnotationScanner;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Mehdi Kamali
 * @since 06/08/2025
 */
public class SpringAnnotationScanner implements AnnotationScanner {

    @Override
    public <T> List<Class<? extends T>> scanForType(Class<T> type, List<String> basePackages, Class<? extends Annotation> annotationClass) throws AnnotationScannerException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(annotationClass));

        List<Class<? extends T>> classes = new ArrayList<>();
        try {
            for (String basePackage : basePackages) {
                for (BeanDefinition bean : scanner.findCandidateComponents(basePackage)) {
                    Class<?> clazz = Class.forName(bean.getBeanClassName());
                    if (type.isAssignableFrom(clazz)) {
                        classes.add(clazz.asSubclass(type));
                    }
                }
            }
        } catch (Exception e) {
            throw new AnnotationScannerException(e);
        }
        return classes;
    }

    @Override
    public List<Method> scanForMethods(List<String> basePackages, Class<? extends Annotation> annotationClass) throws AnnotationScannerException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

        List<Method> methods = new ArrayList<>();
        try {
            for (String basePackage : basePackages) {
                for (BeanDefinition bean : scanner.findCandidateComponents(basePackage)) {
                    Class<?> clazz = Class.forName(bean.getBeanClassName());
                    for (Method method : clazz.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(annotationClass)) {
                            methods.add(method);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new AnnotationScannerException(e);
        }
        return methods;
    }
}
