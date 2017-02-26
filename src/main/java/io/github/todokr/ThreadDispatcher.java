package io.github.todokr;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ThreadDispatcher {

    private static Logger logger = Logger.getLogger(ThreadDispatcher.class.getName());

    public void startDispatching(ServerSocket serverSocket, HttpProtocolFactory protocolFactory) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Runnable protocol = protocolFactory.create(socket);
                Thread thread = new Thread(protocol);
                thread.start();
            } catch (IOException e) {
                logger.severe("Failed to dispatch: " + e.getMessage());
            }
        }
    }
}
