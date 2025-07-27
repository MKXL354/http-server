package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class CustomHttpClient {

    private final String IP = "localhost";
    private final int PORT = 8080;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection() throws IOException {
        clientSocket = new Socket(IP, PORT);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
