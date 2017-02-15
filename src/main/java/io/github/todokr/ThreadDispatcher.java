package io.github.todokr;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ThreadDispatcher implements Dispatcher {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public void startDispatching(ServerSocket serverSocket, ProtocolFactory protocolFactory) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Runnable protocol = protocolFactory.create(socket);
                Thread thread = new Thread(protocol);
                thread.start();
                logger.info("Thread started: " + thread.getName());
            } catch (IOException e) {
                logger.severe("Failed to dispatch: " + e.getMessage());
            }
        }
    }
}
