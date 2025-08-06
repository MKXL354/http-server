package com.mahdy.httpServer.config;

import com.mahdy.httpServer.util.classpathScan.AnnotationScanner;
import com.mahdy.httpServer.util.classpathScan.SpringAnnotationScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mehdi Kamali
 * @since 06/08/2025
 */
@Configuration
@ComponentScan(basePackages = {"com.mahdy.httpServer"})
public class HttpServerAutoConfiguration {

    @Bean
    public AnnotationScanner annotationScanner() {
        return new SpringAnnotationScanner();
    }
}
