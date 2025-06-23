package edu.escuelaing.arsw;

import java.io.*;
import java.net.*;

public class Calculator {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(35000);
        System.out.println("Servidor escuchando en el puerto 35000...");

        try (Socket clientSocket = serverSocket.accept()) {
            System.out.println("Cliente conectado");
            handleConnection(clientSocket);
        }

        serverSocket.close();
    }

    private static void handleConnection(Socket clientSocket) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // Lambdas trigonométricas
        TrigonometricOperation<Double> seno = Math::sin;
        TrigonometricOperation<Double> coseno = Math::cos;
        TrigonometricOperation<Double> tangente = Math::tan;

        String currentOperation = "cos"; // default
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.startsWith("fun:")) {
                String requestedFunction = inputLine.substring(4).trim().toLowerCase();
                if (requestedFunction.equals("sin") || requestedFunction.equals("cos") || requestedFunction.equals("tan")) {
                    currentOperation = requestedFunction;
                    out.println("Función actual cambiada a: " + currentOperation);
                } else {
                    out.println("Error: función '" + requestedFunction + "' no reconocida.");
                }
            } else {
                try {
                    double inputNumber = Double.parseDouble(inputLine);
                    double result = applyTrigonometric(inputNumber, currentOperation, seno, coseno, tangente);
                    out.println("Operación actual: " + currentOperation);
                    out.println("Resultado: " + result);
                } catch (NumberFormatException e) {
                    out.println("Error: entrada no válida. Por favor, ingrese un número.");
                } catch (IllegalArgumentException e) {
                    out.println(e.getMessage());
                }
            }
        }

        in.close();
        out.close();
    }

    private static double applyTrigonometric(
            double input,
            String operation,
            TrigonometricOperation<Double> sinOp,
            TrigonometricOperation<Double> cosOp,
            TrigonometricOperation<Double> tanOp
    ) {
        switch (operation) {
            case "sin":
                return sinOp.TrigonometricOp(input);
            case "cos":
                return cosOp.TrigonometricOp(input);
            case "tan":
                return tanOp.TrigonometricOp(input);
            default:
                throw new IllegalArgumentException("Operación no válida: " + operation);
        }
    }
}
