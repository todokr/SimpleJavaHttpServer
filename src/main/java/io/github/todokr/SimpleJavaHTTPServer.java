package io.github.todokr;

import io.github.todokr.utils.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class SimpleJavaHTTPServer {

    private static Logger logger = new Logger(SimpleJavaHTTPServer.class.getSimpleName());
    private static int PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException {

        AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
        serverChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        serverChannel.bind(new InetSocketAddress(PORT));

        logger.log("HTTP Server Start! Listening at " + PORT + "!");

        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

            @Override
            public void completed(AsynchronousSocketChannel socket, Void attachment) {
                logger.log("accept");
                new HttpProtocol(socket).process();
                return;
            }

            @Override
            public void failed(Throwable e, Void attachment) {
                logger.error("Failed to accept: " + e.getMessage());
            }
        });


        while (true) {
            Thread.sleep(1);
        }
    }

}
