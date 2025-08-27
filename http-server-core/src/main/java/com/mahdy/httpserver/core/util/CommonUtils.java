package com.mahdy.httpserver.core.util;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.security.KeyStore;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
@Slf4j
public class CommonUtils {

    private static final String TLS = "TLS";

    public static boolean isBlank(String string) {
        return string == null || string.isBlank();
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    public static URL getResourceAsUrl(String resourcePath) throws FileNotFoundException {
        URL resourceUrl = CommonUtils.class.getClassLoader().getResource(resourcePath);
        if (resourceUrl == null) {
            throw new FileNotFoundException("resource not found: " + resourcePath);
        }
        return resourceUrl;
    }

    public static InputStream getResourceAsStream(String resourcePath) throws IOException {
        return getResourceAsUrl(resourcePath).openStream();
    }

    public static Path getResourceAsPath(String resourcePath) throws IOException, URISyntaxException {
        return Path.of(getResourceAsUrl(resourcePath).toURI());
    }

    public static File getResourceAsFile(String resourcePath) throws IOException, URISyntaxException {
        return getResourceAsPath(resourcePath).toFile();
    }

    public static SSLContext createSslContextFromKeystore(String keystorePath, String keystorePassword, String keyPassword,
                                                          KeyStoreType keystoreType) throws Exception {
        try (InputStream inputStream = getResourceAsStream(keystorePath)) {
            KeyStore keyStore = KeyStore.getInstance(keystoreType.toString());
            keyStore.load(inputStream, keystorePassword.toCharArray());
            KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            factory.init(keyStore, keyPassword.toCharArray());
            SSLContext sslContext = SSLContext.getInstance(TLS);
            sslContext.init(factory.getKeyManagers(), null, null);
            return sslContext;
        }
    }
}
