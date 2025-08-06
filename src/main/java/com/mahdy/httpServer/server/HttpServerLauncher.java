package com.mahdy.httpServer.server;

import com.mahdy.httpServer.config.HttpServerAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;

/**
 * @author Mehdi Kamali
 * @since 06/08/2025
 */
public class HttpServerLauncher {

    public static void start(String[] args) {
        SpringApplication app = new SpringApplication(HttpServerAutoConfiguration.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
}
