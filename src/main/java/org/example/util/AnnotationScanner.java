package org.example.util;

import org.example.exception.AnnotationScannerException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class AnnotationScanner {

    public List<Method> getAnnotationHandlers(String basePackage, Class<? extends Annotation> annotationClass) {
        List<Method> methods = new ArrayList<>();
        String path = basePackage.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            if (!resources.hasMoreElements()) {
                throw new AnnotationScannerException("failed to find package path " + basePackage);
            }
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("file")) {
                    File directory = new File(resource.toURI());
                    methods.addAll(scanDirectory(directory, basePackage, annotationClass));
                } else if (resource.getProtocol().equals("jar")) {
                    methods.addAll(scanJar(resource, basePackage, annotationClass));
                }
            }
            return methods;
        } catch (Exception e) {
            throw new AnnotationScannerException("failed to scan and register processors", e);
        }
    }

    private List<Method> scanDirectory(File directory, String packageName, Class<? extends Annotation> annotationClass) {
        List<Method> methods = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                methods.addAll(scanDirectory(file, packageName + "." + file.getName(), annotationClass));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                methods.addAll(inspectClass(className, annotationClass));
            }
        }
        return methods;
    }

    private List<Method> scanJar(URL resource, String path, Class<? extends Annotation> annotationClass) throws IOException {
        List<Method> methods = new ArrayList<>();
        String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
        try (JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.startsWith(path) && name.endsWith(".class") && !entry.isDirectory()) {
                    String className = name.replace('/', '.').replace(".class", "");
                    methods.addAll(inspectClass(className, annotationClass));
                }
            }
            return methods;
        }
    }

    private List<Method> inspectClass(String className, Class<? extends Annotation> annotationClass) {
        List<Method> methods = new ArrayList<>();
        try {
            Class<?> clazz = Class.forName(className);
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotationClass)) {
                    methods.add(method);
                }
            }
            return methods;
        } catch (ClassNotFoundException e) {
            throw new AnnotationScannerException("failed to inspect processor class", e);
        }
    }
}
