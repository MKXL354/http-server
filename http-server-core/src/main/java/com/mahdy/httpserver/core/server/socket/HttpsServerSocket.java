package com.mahdy.httpserver.core.server.socket;

import com.mahdy.httpserver.core.exception.base.ApplicationRuntimeException;
import com.mahdy.httpserver.core.model.enumeration.TlsVersion;
import com.mahdy.httpserver.core.util.CommonUtils;
import com.mahdy.httpserver.core.util.KeyStoreType;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
public class HttpsServerSocket implements ServerSocket {

    private final int PORT;
    private final int SOCKET_TIMEOUT_MILLIS;
    private final String KEY_STORE_PATH;
    private final String KEY_STORE_PASSWORD;
    private final String TLS_KEY_PASSWORD;

    private SSLServerSocket serverSocket;

    public HttpsServerSocket(int port, int socketTimeoutMillis, String keyStorePath, String keyStorePassword, String tlsKeyPassword) {
        this.PORT = port;
        this.SOCKET_TIMEOUT_MILLIS = socketTimeoutMillis;
        this.KEY_STORE_PATH = keyStorePath;
        this.KEY_STORE_PASSWORD = keyStorePassword;
        this.TLS_KEY_PASSWORD = tlsKeyPassword;
        start();
    }

    private void start() {
        try {
            SSLContext sslContext = CommonUtils.createSslContextFromKeystore(KEY_STORE_PATH, KEY_STORE_PASSWORD, TLS_KEY_PASSWORD, KeyStoreType.PKCS12);
            serverSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(PORT);
            serverSocket.setEnabledProtocols(Arrays.stream(TlsVersion.values()).map(TlsVersion::getVersion).toArray(String[]::new));
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }
    }

    @Override
    public ClientSocket acceptConnection() throws IOException {
        Socket socket = serverSocket.accept();
        return new ClientSocketImpl(socket, SOCKET_TIMEOUT_MILLIS);
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
