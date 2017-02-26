package io.github.todokr;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class SimpleJavaHTTPServer {

    private static Logger logger = Logger.getLogger(SimpleJavaHTTPServer.class.getName());

    public static void main(String[] args) throws IOException {

        int PORT = 8080;
        ServerSocket serverSocket = new ServerSocket(PORT);
        logger.info("HTTP Server Start! Listening at " + PORT);

        HttpProtocolFactory protocolFactory = new HttpProtocolFactory();
        ThreadDispatcher dispatcher = new ThreadDispatcher();
        dispatcher.startDispatching(serverSocket, protocolFactory);
    }
}
