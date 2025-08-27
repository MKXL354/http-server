package com.mahdy.httpserver.core.util.classpathscan;

import com.mahdy.httpserver.core.exception.AnnotationScannerException;

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
public class CustomAnnotationScanner implements AnnotationScanner {

    private final String PACKAGE_SEPARATOR = ".";
    private final String FILE_SEPARATOR = "/";
    private final String JAR_SEPARATOR = "!";
    private final String FILE_PROTOCOL = "file";
    private final String JAR_PROTOCOL = "jar";
    private final String JAR_PREFIX = "file:";
    private final String CLASS_SUFFIX = ".class";

    @Override
    public <T> List<Class<? extends T>> scanForType(Class<T> type, List<String> basePackages, Class<? extends Annotation> annotationClass)
            throws AnnotationScannerException {
        List<Class<? extends T>> classes = new ArrayList<>();
        try {
            for (String basePackage : basePackages) {
                for (String className : scanForClassNames(basePackage)) {
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(annotationClass) && type.isAssignableFrom(clazz)) {
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
        List<Method> methods = new ArrayList<>();
        try {
            for (String basePackage : basePackages) {
                for (String className : scanForClassNames(basePackage)) {
                    Class<?> clazz = Class.forName(className);
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

    private List<String> scanForClassNames(String basePackage) throws Exception {
        List<String> classNames = new ArrayList<>();
        String path = basePackage.replace(PACKAGE_SEPARATOR.charAt(0), FILE_SEPARATOR.charAt(0));
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);
        if (!resources.hasMoreElements()) {
            throw new AnnotationScannerException("failed to find package path " + basePackage);
        }
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals(FILE_PROTOCOL)) {
                File directory = new File(resource.toURI());
                classNames.addAll(scanDirectoryForClassNames(directory, basePackage));
            } else if (resource.getProtocol().equals(JAR_PROTOCOL)) {
                classNames.addAll(scanJarForClassNames(resource, basePackage));
            }
        }
        return classNames;
    }

    private List<String> scanDirectoryForClassNames(File directory, String packageName) {
        List<String> classNames = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                classNames.addAll(scanDirectoryForClassNames(file, packageName + PACKAGE_SEPARATOR + file.getName()));
            } else if (file.getName().endsWith(CLASS_SUFFIX)) {
                String className = packageName + '.' + file.getName().replace(CLASS_SUFFIX, "");
                classNames.add(className);
            }
        }
        return classNames;
    }

    private List<String> scanJarForClassNames(URL resource, String path) throws IOException {
        List<String> classNames = new ArrayList<>();
        String jarPath = resource.getPath().substring(JAR_PREFIX.length(), resource.getPath().indexOf(JAR_SEPARATOR));
        try (JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.startsWith(path) && name.endsWith(CLASS_SUFFIX) && !entry.isDirectory()) {
                    String className = name.replace(FILE_SEPARATOR.charAt(0), PACKAGE_SEPARATOR.charAt(0)).replace(CLASS_SUFFIX, "");
                    classNames.add(className);
                }
            }
            return classNames;
        }
    }
}
