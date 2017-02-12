package io.github.todokr;

import java.io.IOException;
import java.net.Socket;

import io.github.todokr.utils.Logger;

public class HttpProtocol implements Runnable {

    private Socket socket;
    private Logger logger;
    private RequestHandler requestHandler;

    {
        this.requestHandler = new HttpRequestHandler();
    }

    HttpProtocol(Socket socket, Logger logger) {
        this.socket = socket;
        this.logger = logger;
    }

    public void run() {
        try {
            HttpRequest request = new HttpRequest(socket.getInputStream());
            HttpResponse response = requestHandler.handleRequest(request);
            response.writeTo(socket.getOutputStream());
        } catch (IOException e) {
            logger.log("Failed to process: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }
}
