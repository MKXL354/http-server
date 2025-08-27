package com.mahdy.httpserver.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Mehdi Kamali
 * @since 06/08/2025
 */
@ConfigurationProperties(prefix = "http.server.internal")
@Getter
@Setter
public class InternalRegistryProperties {

    private List<String> defaultMiddlewarePackages;
    private List<String> defaultRoutingPackages;
    private List<String> defaultExceptionHandlingPackages;
}
