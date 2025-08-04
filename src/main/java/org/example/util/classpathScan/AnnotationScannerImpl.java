package org.example.util.classpathScan;

import org.example.exception.AnnotationScannerException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class AnnotationScannerImpl implements AnnotationScanner {

    @Override
    public <T> Set<Class<? extends T>> scanForType(Class<T> type, String basePackage, Class<? extends Annotation> annotationClass) {
        try {
            Set<String> classNames = scanForClassNames(basePackage);
            Set<Class<? extends T>> classes = new HashSet<>();
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(annotationClass) && type.isAssignableFrom(clazz)) {
                    classes.add(clazz.asSubclass(type));
                }
            }
            return classes;
        } catch (Exception e) {
            throw new AnnotationScannerException("Failed to scan types", e);
        }
    }

    @Override
    public Set<Method> scanForMethods(String basePackage, Class<? extends Annotation> annotationClass) {
        try {
            Set<String> classNames = scanForClassNames(basePackage);
            Set<Method> methods = new HashSet<>();
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(annotationClass)) {
                        methods.add(method);
                    }
                }
            }
            return methods;
        } catch (Exception e) {
            throw new AnnotationScannerException("failed to scan methods", e);
        }
    }

    private Set<String> scanForClassNames(String basePackage) throws Exception {
        Set<String> classNames = new HashSet<>();
        String path = basePackage.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);
        if (!resources.hasMoreElements()) {
            throw new AnnotationScannerException("failed to find package path " + basePackage);
        }
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals("file")) {
                File directory = new File(resource.toURI());
                classNames.addAll(scanDirectoryForClassNames(directory, basePackage));
            } else if (resource.getProtocol().equals("jar")) {
                classNames.addAll(scanJarForClassNames(resource, basePackage));
            }
        }
        return classNames;
    }

    private Set<String> scanDirectoryForClassNames(File directory, String packageName) {
        Set<String> classNames = new HashSet<>();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                classNames.addAll(scanDirectoryForClassNames(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
        return classNames;
    }

    private Set<String> scanJarForClassNames(URL resource, String path) throws IOException {
        Set<String> classNames = new HashSet<>();
        String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
        try (JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.startsWith(path) && name.endsWith(".class") && !entry.isDirectory()) {
                    String className = name.replace('/', '.').replace(".class", "");
                    classNames.add(className);
                }
            }
            return classNames;
        }
    }
}
