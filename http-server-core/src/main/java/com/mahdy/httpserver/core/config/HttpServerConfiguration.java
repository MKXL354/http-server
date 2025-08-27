package com.mahdy.httpserver.core.config;

import com.mahdy.httpserver.core.exceptionhandling.ExceptionHandlingRegistry;
import com.mahdy.httpserver.core.exceptionhandling.ExceptionHandlingRegistryImpl;
import com.mahdy.httpserver.core.middleware.MiddlewareRegistry;
import com.mahdy.httpserver.core.middleware.MiddlewareRegistryImpl;
import com.mahdy.httpserver.core.routing.RoutingRegistry;
import com.mahdy.httpserver.core.routing.RoutingRegistryImpl;
import com.mahdy.httpserver.core.util.classpathscan.AnnotationScanner;
import com.mahdy.httpserver.core.util.classpathscan.SpringAnnotationScanner;
import com.mahdy.httpserver.core.validation.handler.ExceptionHandlerMethodValidator;
import com.mahdy.httpserver.core.validation.handler.ProcessorMethodValidator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mehdi Kamali
 * @since 06/08/2025
 */
@Configuration
@EnableConfigurationProperties({InternalRegistryProperties.class, UserRegistryProperties.class})
@ComponentScan(basePackages = {"com.mahdy.httpserver"})
public class HttpServerConfiguration {

    @Bean
    public AnnotationScanner annotationScanner() {
        return new SpringAnnotationScanner();
    }

    @Bean
    public MiddlewareRegistry middlewareRegistry(AnnotationScanner scanner, ApplicationContext context,
                                                 InternalRegistryProperties internalRegistryProperties,
                                                 UserRegistryProperties userRegistryProperties) {
        List<String> packages = new ArrayList<>();
        packages.addAll(internalRegistryProperties.getDefaultMiddlewarePackages());
        packages.addAll(userRegistryProperties.getMiddlewarePackages());
        return new MiddlewareRegistryImpl(packages, scanner, context);
    }

    @Bean
    public RoutingRegistry routingRegistry(AnnotationScanner scanner, ApplicationContext context, ProcessorMethodValidator methodValidator,
                                           InternalRegistryProperties internalRegistryProperties,
                                           UserRegistryProperties userRegistryProperties) {
        List<String> packages = new ArrayList<>();
        packages.addAll(internalRegistryProperties.getDefaultRoutingPackages());
        packages.addAll(userRegistryProperties.getRoutingPackages());
        return new RoutingRegistryImpl(packages, scanner, context, methodValidator);
    }

    @Bean
    public ExceptionHandlingRegistry exceptionHandlingRegistry(AnnotationScanner scanner, ApplicationContext context, ExceptionHandlerMethodValidator methodValidator,
                                                               InternalRegistryProperties internalRegistryProperties,
                                                               UserRegistryProperties userRegistryProperties) {
        List<String> packages = new ArrayList<>();
        packages.addAll(internalRegistryProperties.getDefaultExceptionHandlingPackages());
        packages.addAll(userRegistryProperties.getExceptionHandlingPackages());
        return new ExceptionHandlingRegistryImpl(packages, scanner, context, methodValidator);
    }
}
