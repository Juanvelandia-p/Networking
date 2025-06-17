package edu.escuelaing.arsw;

import java.net.URL;
/**
 * Hello world!
 *
 */
public class Exercise1 
{
    public static void main( String[] args )
    {
        try {
            URL url = new URL("http://ldbn.escuelaing.edu.co:80/index.html");

            System.out.println("URL completa: " + url.toString());
            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Authority: " + url.getAuthority());
            System.out.println("Host: " + url.getHost());
            System.out.println("Port: " + url.getPort());
            System.out.println("Path: " + url.getPath());
            System.out.println("Query: " + url.getQuery());
            System.out.println("File: " + url.getFile());
            System.out.println("Ref (fragment): " + url.getRef());

        } catch (Exception e) {
            System.out.println("Error al analizar la URL: " + e.getMessage());
        }
    }
}
