package com.mahdy.httpserver.starter.spring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Mehdi Kamali
 * @since 06/08/2025
 */
@ConfigurationProperties(prefix = "http.server")
@Getter
@Setter
public class UserRegistryProperties {

    private List<String> middlewarePackages;
    private List<String> routingPackages;
    private List<String> exceptionHandlingPackages;
}
