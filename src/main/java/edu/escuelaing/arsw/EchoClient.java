package edu.escuelaing.arsw;

import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket("127.0.0.1", 35000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don’t know about host.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for the connection to: localhost.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println("Connected to server. Type a number and press Enter. Type 'bye' to exit.");

        while ((userInput = stdIn.readLine()) != null) {
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            }

            out.println(userInput);
            String response = in.readLine();
            System.out.println("Server response: " + response);
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
