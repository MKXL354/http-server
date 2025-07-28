package org.example.socket;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface ClientSocket extends Closeable {

    OutputStream getOutputStream() throws IOException;

    InputStream getInputStream() throws IOException;

    void close() throws IOException;
}
