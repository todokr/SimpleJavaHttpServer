package io.github.todokr;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class SimpleJavaHTTPServer {

    public static void main(String[] args) throws IOException {

        Logger logger = Logger.getLogger(SimpleJavaHTTPServer.class.getName());

        int PORT = 8080;
        ServerSocket serverSocket = new ServerSocket(PORT);
        logger.info("HTTP Server is listening at " + PORT + " \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89");

        ProtocolFactory protocolFactory = new HttpProtocolFactory();
        //Dispatcher dispatcher = new ThreadDispatcher(); // リクエストごとに無限にスレッドを生成するディスパッチャ
        Dispatcher dispatcher = new RecyclingDispatcher(); // 予め生成した固定数のスレッドを再利用するディスパッチャ
        dispatcher.startDispatching(serverSocket, protocolFactory);

    }
}
