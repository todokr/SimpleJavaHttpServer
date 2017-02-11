package io.github.todokr;


import io.github.todokr.utils.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleJavaHTTPServer {

    public static void main(String[] args) throws IOException {

        int PORT = 8080;
        Logger logger = new Logger(System.out, "SimpleHTTPServer");

        ServerSocket serverSocket = new ServerSocket(PORT);
        logger.log("HTTP Server is listening at " + PORT + "...");

        while (true) {
            try (Socket socket = serverSocket.accept();
                InputStream input = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
            ) {
                Request request = new Request(input);
                Response response = new Response(request);

                System.out.println(response.getContentType());
                //response.writeTo(out);
            }
        }
    }
}
