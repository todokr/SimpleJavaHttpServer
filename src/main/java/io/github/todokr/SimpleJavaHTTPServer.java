package io.github.todokr;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class SimpleJavaHTTPServer {

    private static Logger logger = Logger.getLogger(SimpleJavaHTTPServer.class.getName());

    public static void main(String[] args) throws IOException {

        int PORT = 8080;
        ServerSocket serverSocket = new ServerSocket(PORT);
        logger.info("HTTP Server Start! Listening at " + PORT);

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new HttpProtocol(socket));
                thread.start();
            } catch (IOException e) {
                logger.severe("Failed to dispatch: " + e.getMessage());
            }
        }
    }
}
