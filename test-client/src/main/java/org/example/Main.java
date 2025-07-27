package org.example;

import org.example.client.CustomHttpClient;

import java.io.IOException;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public class Main {

    public static void main(String[] args) throws IOException {
        CustomHttpClient client =  new CustomHttpClient();
        client.startConnection();
        System.out.println(client.sendMessage("hello server"));
        client.stopConnection();
    }
}
