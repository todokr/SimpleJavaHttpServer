package io.github.todokr;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class SimpleJavaHTTPServer {

    public static void main(String[] args) throws IOException {

        final Logger logger = Logger.getLogger(SimpleJavaHTTPServer.class.getName());
        int PORT = 8080;

        ServerSocket serverSocket = new ServerSocket(PORT);
        logger.info("HTTP Server is listening at " + PORT + "...");

        ProtocolFactory protocolFactory = new HttpProtocolFactory(); // HTTPプロトコルに則って処理を行う
        //Dispatcher dispatcher = new ThreadDispatcher(); // リクエストごとに無限にThreadを生成するディスパッチャ
        Dispatcher dispatcher = new ThreadPoolDispatcher(); // スレッドプールを使うディスパッチャ
        dispatcher.startDispatching(serverSocket, protocolFactory);

    }
}
