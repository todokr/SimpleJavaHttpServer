package io.github.todokr;

import io.github.todokr.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPoolDispatcher implements Dispatcher {

    public static final int DEFAULT_POOL_SIZE = 10;
    private final int poolSize;

    ThreadPoolDispatcher(int poolSize) {
        this.poolSize = poolSize;
    }

    ThreadPoolDispatcher() {
        this(DEFAULT_POOL_SIZE);
    }

    public void startDispatching(ServerSocket serverSocket, Logger logger, ProtocolFactory protocolFactory) {
        for (int i = 0; i < poolSize; i++) {
            Thread thread = new Thread() {
                public void run() {
                    dispatchLoop(serverSocket, logger, protocolFactory);
                }
            };
            thread.start();
            logger.log("Thread started: " + thread.getName());
        }
    }

    private void dispatchLoop(ServerSocket serverSocket, Logger logger, ProtocolFactory protocolFactory) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Runnable protocol = protocolFactory.create(socket, logger);
                protocol.run();
                logger.log(Thread.currentThread().getName());
            } catch (IOException e) {
                logger.log("Failed to dispatch: " + e.getMessage());
            }
        }
    }
}
