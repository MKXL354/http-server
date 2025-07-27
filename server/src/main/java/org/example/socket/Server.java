package org.example.socket;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface Server {

    ClientSocket acceptConnection() throws IOException;

    void close() throws IOException;
}
