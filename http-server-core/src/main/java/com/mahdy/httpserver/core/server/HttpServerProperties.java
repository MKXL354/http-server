package com.mahdy.httpserver.core.server;

import lombok.Data;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
@Data
public class HttpServerProperties {

    private final int port;
    private final int socketTimeoutMillis;
    private final boolean httpsEnabled;
    private final String keyStorePath;
    private final String keyStorePassword;
    private final String tlsKeyPassword;
}
