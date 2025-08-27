package com.mahdy.httpserver.starter.spring.config;

import com.mahdy.httpserver.core.exceptionhandling.DefaultExceptionHandler;
import com.mahdy.httpserver.core.exceptionhandling.ExceptionHandlingRegistry;
import com.mahdy.httpserver.core.exceptionhandling.ExceptionHandlingRegistryImpl;
import com.mahdy.httpserver.core.executor.ServerLoopExecutionManager;
import com.mahdy.httpserver.core.executor.SingleThreadServerLoopExecutionManager;
import com.mahdy.httpserver.core.executor.TaskExecutionManager;
import com.mahdy.httpserver.core.executor.VirtualThreadTaskExecutionManager;
import com.mahdy.httpserver.core.io.request.HttpRequestReader;
import com.mahdy.httpserver.core.io.request.HttpRequestReaderImpl;
import com.mahdy.httpserver.core.io.response.HttpResponseWriter;
import com.mahdy.httpserver.core.io.response.HttpResponseWriterImpl;
import com.mahdy.httpserver.core.lifecycle.FullControlHttpLifeCycle;
import com.mahdy.httpserver.core.lifecycle.HttpLifeCycleTemplate;
import com.mahdy.httpserver.core.lifecycle.ObjectResolver;
import com.mahdy.httpserver.core.middleware.*;
import com.mahdy.httpserver.core.routing.RoutingRegistry;
import com.mahdy.httpserver.core.routing.RoutingRegistryImpl;
import com.mahdy.httpserver.core.routing.SimpleFullControlProcessor;
import com.mahdy.httpserver.core.server.HttpServer;
import com.mahdy.httpserver.core.server.HttpServerProperties;
import com.mahdy.httpserver.core.util.classpathscan.AnnotationScanner;
import com.mahdy.httpserver.core.util.classpathscan.CustomAnnotationScanner;
import com.mahdy.httpserver.core.validation.handler.ExceptionHandlerMethodValidator;
import com.mahdy.httpserver.core.validation.handler.ExceptionHandlerMethodValidatorImpl;
import com.mahdy.httpserver.core.validation.handler.FullControlProcessorMethodValidator;
import com.mahdy.httpserver.core.validation.handler.ProcessorMethodValidator;
import com.mahdy.httpserver.starter.spring.lifecycle.SpringObjectResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mehdi Kamali
 * @since 06/08/2025
 */
@Configuration
@EnableConfigurationProperties({InternalRegistryProperties.class, UserRegistryProperties.class})
@ComponentScan(basePackages = "com.mahdy.httpserver.starter.spring")
@PropertySource("classpath:config/core.properties")
public class HttpServerAutoConfiguration {

    @Bean
    public ServerLoopExecutionManager serverLoopExecutionManager() {
        return new SingleThreadServerLoopExecutionManager();
    }

    @Bean
    public TaskExecutionManager taskExecutionManager() {
        return new VirtualThreadTaskExecutionManager();
    }

    @Bean
    public HttpServerProperties httpServerProperties(@Value("${http.server.socket.port}") int port, @Value("${http.server.socket.timeout}") int socketTimeoutMillis,
                                                     @Value("${http.server.socket.https-enabled}") boolean httpsEnabled,
                                                     @Value("${http.server.https.key-store-path}") String keyStorePath,
                                                     @Value("${http.server.https.key-store-password}") String keyStorePassword,
                                                     @Value("${http.server.https.tls-key-password}") String keyPassword) {
        return new HttpServerProperties(port, socketTimeoutMillis, httpsEnabled, keyStorePath, keyStorePassword, keyPassword);
    }

    @Bean
    public HttpServer httpServer(HttpServerProperties serverProperties, ServerLoopExecutionManager serverLoopExecutionManager,
                                 TaskExecutionManager taskExecutionManager, HttpLifeCycleTemplate httpLifeCycleTemplate) {
        return new HttpServer(serverProperties, serverLoopExecutionManager, taskExecutionManager, httpLifeCycleTemplate);
    }

    @Bean
    public AnnotationScanner annotationScanner() {
        return new CustomAnnotationScanner();
    }

    @Bean
    public ObjectResolver objectResolver(ApplicationContext applicationContext) {
        return new SpringObjectResolver(applicationContext);
    }

    @Bean
    public ExceptionHandlerMethodValidator exceptionHandlerMethodValidator() {
        return new ExceptionHandlerMethodValidatorImpl();
    }

    @Bean
    public ProcessorMethodValidator processorMethodValidator() {
        return new FullControlProcessorMethodValidator();
    }

    @Bean
    public MiddlewareRegistry middlewareRegistry(AnnotationScanner scanner, ObjectResolver objectResolver,
                                                 InternalRegistryProperties internalRegistryProperties,
                                                 UserRegistryProperties userRegistryProperties) {
        List<String> packages = new ArrayList<>();
        packages.addAll(internalRegistryProperties.getDefaultMiddlewarePackages());
        packages.addAll(userRegistryProperties.getMiddlewarePackages());
        return new MiddlewareRegistryImpl(packages, scanner, objectResolver);
    }

    @Bean
    public RoutingRegistry routingRegistry(AnnotationScanner scanner, ObjectResolver resolver, ProcessorMethodValidator methodValidator,
                                           InternalRegistryProperties internalRegistryProperties,
                                           UserRegistryProperties userRegistryProperties) {
        List<String> packages = new ArrayList<>();
        packages.addAll(internalRegistryProperties.getDefaultRoutingPackages());
        packages.addAll(userRegistryProperties.getRoutingPackages());
        return new RoutingRegistryImpl(packages, scanner, resolver, methodValidator);
    }

    @Bean
    public ExceptionHandlingRegistry exceptionHandlingRegistry(AnnotationScanner scanner, ObjectResolver objectResolver,
                                                               ExceptionHandlerMethodValidator methodValidator,
                                                               InternalRegistryProperties internalRegistryProperties,
                                                               UserRegistryProperties userRegistryProperties) {
        List<String> packages = new ArrayList<>();
        packages.addAll(internalRegistryProperties.getDefaultExceptionHandlingPackages());
        packages.addAll(userRegistryProperties.getExceptionHandlingPackages());
        return new ExceptionHandlingRegistryImpl(packages, scanner, objectResolver, methodValidator);
    }

    @Bean
    public HttpLifeCycleTemplate httpLifeCycleTemplate(RoutingRegistry routingRegistry, MiddlewareRegistry middlewareRegistry,
                                                       ExceptionHandlingRegistry exceptionHandlingRegistry, HttpRequestReader httpRequestReader,
                                                       HttpResponseWriter httpResponseWriter) {
        return new FullControlHttpLifeCycle(routingRegistry, middlewareRegistry, exceptionHandlingRegistry,
                httpRequestReader, httpResponseWriter);
    }

    @Bean
    public HttpRequestReader httpRequestReader(@Value("${http.server.http.max-content-length}") int maxContentLength) {
        return new HttpRequestReaderImpl(maxContentLength);
    }

    @Bean
    public HttpResponseWriter httpResponseWriter() {
        return new HttpResponseWriterImpl();
    }

    @Bean
    public FillDefaultHttpResponseValuesMiddleware fillDefaultHttpResponseValuesMiddleware() {
        return new FillDefaultHttpResponseValuesMiddleware();
    }

    @Bean
    public FillHttpRequestContextMiddleware fillHttpRequestContextMiddleware() {
        return new FillHttpRequestContextMiddleware();
    }

    @Bean
    public FillHttpResponseHeadersMiddleware fillHttpResponseHeadersMiddleware() {
        return new FillHttpResponseHeadersMiddleware();
    }

    @Bean
    public SimpleFullControlProcessor simpleFullControlProcessor() {
        return new SimpleFullControlProcessor();
    }

    @Bean
    public DefaultExceptionHandler defaultExceptionHandler() {
        return new DefaultExceptionHandler();
    }
}
