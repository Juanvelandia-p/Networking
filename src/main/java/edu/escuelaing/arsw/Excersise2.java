package edu.escuelaing.arsw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Excersise2 {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Por favor proporcione una URL como argumento");
            System.exit(1);
        }

        URL url = new URL(args[0]);
        File outputFile = new File("resultado.html");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                writer.write(inputLine);
                writer.newLine();
            }
            System.out.println("El contenido ha sido guardado en resultado.html");
        } catch (IOException x) {
            System.err.println("Error: " + x.getMessage());
        }
    }
}