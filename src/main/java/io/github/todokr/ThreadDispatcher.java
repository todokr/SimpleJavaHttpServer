package io.github.todokr;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import io.github.todokr.utils.Logger;

public class ThreadDispatcher implements Dispatcher {

    public void startDispatching(ServerSocket serverSocket, Logger logger, ProtocolFactory protocolFactory) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Runnable protocol = protocolFactory.create(socket, logger);
                Thread thread = new Thread(protocol);
                thread.start();
                logger.log("Thread: " + thread.getName());
            } catch (IOException e) {
                logger.log("Failed to dispatch: " + e.getMessage());
            }
        }
    }
}
