package io.github.todokr;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ThreadPoolDispatcher implements Dispatcher {

    private static final int DEFAULT_POOL_SIZE = 10;
    private final int poolSize;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    ThreadPoolDispatcher(int poolSize) {
        this.poolSize = poolSize;
    }

    ThreadPoolDispatcher() {
        this(DEFAULT_POOL_SIZE);
    }

    public void startDispatching(ServerSocket serverSocket, ProtocolFactory protocolFactory) {
        for (int i = 0; i < poolSize; i++) {
            Thread thread = new Thread() {
                public void run() {
                    repeatProcessing(serverSocket, protocolFactory);
                }
            };
            thread.start();
        }
        logger.info(poolSize + " threads started...");
    }

    private void repeatProcessing(ServerSocket serverSocket, ProtocolFactory protocolFactory) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Runnable protocol = protocolFactory.create(socket);
                protocol.run(); // 同一スレッドでの実行させたいのでstart()ではなくrun()
            } catch (IOException e) {
                logger.severe("Failed to dispatch: " + e.getMessage());
            }
        }
    }
}
