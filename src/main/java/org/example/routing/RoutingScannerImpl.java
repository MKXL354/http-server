package org.example.routing;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.Routing;
import org.example.exception.ApplicationRuntimeException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RoutingScannerImpl implements RoutingScanner {

    private final String BASE_PACKAGE = "org.example.processor";

    private final ApplicationContext applicationContext;
    private final RoutingRegistry routingRegistry;

    @PostConstruct
    @Override
    public void scanAndRegister() {
        String path = BASE_PACKAGE.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            if (!resources.hasMoreElements()) {
                throw new ApplicationRuntimeException("failed to find package path " + BASE_PACKAGE);
            }
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("file")) {
                    File directory = new File(resource.toURI());
                    scanDirectory(directory, BASE_PACKAGE);
                } else if (resource.getProtocol().equals("jar")) {
                    scanJar(resource, path);
                }
            }
        } catch (Exception e) {
            throw new ApplicationRuntimeException("failed to scan and register processors", e);
        }
    }

    private void scanDirectory(File directory, String packageName) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                scanDirectory(file, packageName + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                inspectClass(className);
            }
        }
    }

    private void scanJar(URL resource, String path) throws IOException {
        String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
        try (JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.startsWith(path) && name.endsWith(".class") && !entry.isDirectory()) {
                    String className = name.replace('/', '.').replace(".class", "");
                    inspectClass(className);
                }
            }
        }
    }

    private void inspectClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Routing.class)) {
                    Routing request = method.getAnnotation(Routing.class);
                    Object instance = applicationContext.getBean(clazz);
                    routingRegistry.register(request.httpMethod(), request.path(), new ProcessorMethod(instance, method));
                }
            }
        } catch (ClassNotFoundException e) {
            throw new ApplicationRuntimeException("failed to inspect processor classes", e);
        }
    }
}
