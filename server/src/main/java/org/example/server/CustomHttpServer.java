package org.example.server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.socket.ClientSocket;
import org.example.socket.Server;
import org.example.socket.ServerImpl;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class CustomHttpServer {

    private final int PORT = 8080;

    private Server server;
    private ClientSocket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @PostConstruct
    public void start() throws IOException {
        server = new ServerImpl(PORT);
        clientSocket = server.acceptConnection();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();
        if ("hello server".equals(greeting)) {
            out.println("hello client");
        }
        else {
            out.println("unrecognised greeting");
        }
    }

    @PreDestroy
    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        server.close();
    }
}
