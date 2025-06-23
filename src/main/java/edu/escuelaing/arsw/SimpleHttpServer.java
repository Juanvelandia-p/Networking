package edu.escuelaing.arsw;

import java.io.*;
import java.net.*;
import java.nio.file.*;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        // 1. Crear el servidor en el puerto 8080
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Servidor escuchando en puerto 8080...");

        while (true) {
            // 2. Esperar la conexión de un cliente
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado");

            // 3. Procesar la conexión
            handleClient(clientSocket);
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            // 4. Preparar para leer la solicitud del navegador
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // 5. Preparar para escribir la respuesta al navegador
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream())
        ) {
            // 6. Leer la primera línea del HTTP Request (ej: GET /descargar?url=https://example.com HTTP/1.1)
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;

            System.out.println("Petición recibida: " + requestLine);

            // 7. Verificar si es una solicitud al endpoint /descargar
            if (requestLine.startsWith("GET /descargar?")) {
                // 8. Extraer la URL desde la query
                String query = requestLine.split(" ")[1]; // parte como: /descargar?url=https://example.com
                String rawUrl = URLDecoder.decode(query.substring(query.indexOf("url=") + 4), "UTF-8");

                // 9. Descargar el contenido HTML de esa URL
                String content = downloadHtmlContent(rawUrl);

                // 10. Guardarlo como resultado.html
                Files.writeString(Paths.get("resultado.html"), content);
                System.out.println("Guardado como resultado.html");

                // 11. Devolver respuesta al navegador
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html; charset=UTF-8");
                out.println();
                out.println("<html><body>");
                out.println("<h2>Contenido descargado correctamente</h2>");
                out.println("<p>Revisar el archivo <code>resultado.html</code> en la misma carpeta.</p>");
                out.println("</body></html>");
            } else {
                // 12. Si no es una ruta válida, devolver mensaje por defecto
                out.println("HTTP/1.1 404 Not Found");
                out.println("Content-Type: text/html; charset=UTF-8");
                out.println();
                out.println("<html><body><h2>Ruta no válida</h2><p>Usa el formulario HTML para enviar una URL.</p></body></html>");
            }

            // 13. Enviar respuesta al navegador
            out.flush();
            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Función para conectarse a una URL y obtener su HTML como texto
    private static String downloadHtmlContent(String urlStr) throws IOException {
        StringBuilder content = new StringBuilder();

        // 1. Crear un objeto URL con el string recibido
        URL url = new URL(urlStr);

        // 2. Abrir una conexión para lectura
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        // 3. Leer el contenido línea por línea
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }

        // 4. Cerrar lector
        reader.close();

        // 5. Devolver el contenido como String
        return content.toString();
    }
}
