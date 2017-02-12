package io.github.todokr;

import java.io.IOException;
import java.net.ServerSocket;


import io.github.todokr.utils.Logger;

public class SimpleJavaHTTPServer {

    public static void main(String[] args) throws IOException {

        int PORT = 8080;
        Logger logger = new Logger(System.out, "SimpleHTTPServer");

        ServerSocket serverSocket = new ServerSocket(PORT);
        logger.log("HTTP Server is listening at " + PORT + "...");

        ProtocolFactory protocolFactory = new HttpProtocolFactory(); // HTTPプロトコルに則って処理を行う
        //Dispatcher dispatcher = new ThreadDispatcher(); // リクエストごとに無限にThreadを生成するディスパッチャ
        Dispatcher dispatcher = new ThreadPoolDispatcher(); // スレッドプールを使うディスパッチャ
        dispatcher.startDispatching(serverSocket, logger, protocolFactory);
    }
}
