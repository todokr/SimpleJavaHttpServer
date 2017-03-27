package io.github.todokr;

import io.github.todokr.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleJavaHTTPServer {

    private static Logger logger = new Logger(SimpleJavaHTTPServer.class.getSimpleName());
    private static int PORT = 8080;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        logger.log("HTTP Server Start! Listening at " + PORT + "!");

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new HttpProtocol(socket));
                thread.start();
            } catch (IOException e) {
                logger.error("Failed to dispatch: " + e.getMessage());
            }
        }
    }
}
