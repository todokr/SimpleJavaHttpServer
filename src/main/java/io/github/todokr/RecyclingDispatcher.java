package io.github.todokr;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class RecyclingDispatcher implements Dispatcher {

    private static final int DEFAULT_POOL_SIZE = 10;
    private final int numberOfThread;
    private static final Logger logger = Logger.getLogger(RecyclingDispatcher.class.getName());

    RecyclingDispatcher(int numberOfThread) {
        this.numberOfThread = numberOfThread;
    }

    RecyclingDispatcher() {
        this(DEFAULT_POOL_SIZE);
    }

    public void startDispatching(ServerSocket serverSocket, ProtocolFactory protocolFactory) {
        for (int i = 0; i < numberOfThread; i++) {
            Thread thread = new Thread() {
                public void run() {
                    repeatProcessing(serverSocket, protocolFactory);
                }
            };
            thread.start();
        }
        logger.info(numberOfThread + " threads started...");
    }

    private void repeatProcessing(ServerSocket serverSocket, ProtocolFactory protocolFactory) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Runnable protocol = protocolFactory.create(socket);
                protocol.run(); // 同一スレッドで実行させたいのでstart()ではなくrun()
            } catch (IOException e) {
                logger.severe("Failed to dispatch: " + e.getMessage());
            }
        }
    }
}
