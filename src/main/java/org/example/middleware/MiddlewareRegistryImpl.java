package org.example.middleware;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.annotation.Middleware;
import org.example.exception.DuplicateMiddlewareOrderRegisteredException;
import org.example.util.classpathScan.AnnotationScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
@Component
@RequiredArgsConstructor
public class MiddlewareRegistryImpl implements MiddlewareRegistry {

    private final String BASE_PACKAGE = "org.example.middleware";

    private final AnnotationScanner annotationScanner;
    private final ApplicationContext applicationContext;

    @Getter
    private PreProcessMiddleware preProcessMiddlewareChainStart;
    @Getter
    private PostProcessMiddleware postProcessMiddlewareChainStart;

    @PostConstruct
    @Override
    public void fillRegistry() {
        preProcessMiddlewareChainStart = getChainStart(PreProcessMiddleware.class);
        postProcessMiddlewareChainStart = getChainStart(PostProcessMiddleware.class);
    }

    private <T extends ProcessMiddleware> T getChainStart(Class<T> type) {
        List<Class<? extends T>> preProcessorClasses = annotationScanner.scanForType(type,
                BASE_PACKAGE, Middleware.class);
        HashMap<Integer, Class<?>> seenOrders = new HashMap<>();
        for (Class<?> clazz : preProcessorClasses) {
            Integer order = clazz.getAnnotation(Middleware.class).order();
            if (seenOrders.containsKey(order)) {
                throw new DuplicateMiddlewareOrderRegisteredException(String.format(
                        "Duplicate Middlewares %s and %s registered for order value: %d ", seenOrders.get(order).getCanonicalName(), clazz.getCanonicalName(), order)
                );
            }
            seenOrders.put(order, clazz);
        }
        preProcessorClasses.sort(Comparator.comparingInt(preProcessor -> preProcessor.getAnnotation(Middleware.class).order()));
        List<T> preProcessorObjects = new ArrayList<>();
        for (Class<? extends T> preProcessorClass : preProcessorClasses) {
            preProcessorObjects.add(applicationContext.getBean(preProcessorClass));
        }
        for (int i = 0; i < preProcessorObjects.size() - 1; i++) {
            preProcessorObjects.get(i).setNext(preProcessorObjects.get(i + 1));
        }
        return preProcessorObjects.getFirst();
    }
}

