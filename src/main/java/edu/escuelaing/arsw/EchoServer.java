package edu.escuelaing.arsw;

import java.io.*;
import java.net.*;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(35000);
        System.out.println("Servidor escuchando en el puerto 35000...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Cliente conectado");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // Lambda para calcular el cuadrado de un número tipo Double
        NumericProcessor<Double> squareProcessor = number -> Math.pow(number.doubleValue(), 2);

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibido: " + inputLine);
            try {
                Double number = Double.parseDouble(inputLine.trim());
                double result = squareProcessor.process(number);
                out.println(result);
            } catch (NumberFormatException e) {
                out.println("Error: el valor ingresado no es un número válido.");
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
